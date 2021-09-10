package server.serverside;

import client.CommandNet;
import server.lib.Wrapper;
import server.commands.Executor;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.nio.ByteBuffer;
import java.nio.channels.DatagramChannel;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.util.*;

// объект класса управляет полученными данными от клиента; выполняет полученную команду и сразу отправляет результат
public class DataManager {
    Selector selector;
    CommandNet receivedCommand;
    LinkedList<DataHolder> queue = new LinkedList<>();

    public void manageData() {

        selector = Server.getSelector();
        while (Server.running) {
            try {
                selector.select(50);
                Set<SelectionKey> selectedKeys = selector.selectedKeys();
                Iterator<SelectionKey> keyIterator = selectedKeys.iterator();
                while (keyIterator.hasNext()) {
                    SelectionKey key = keyIterator.next();
                    keyIterator.remove();
                    if (key.isValid()) {
                        if (key.isReadable()) {
                            receiveData(key);
                        }
//                        } else if (key.isWritable() && inProgress.contains(key)) {
//                            requestData(key);
//                            inProgress.remove(key);
//                        }
                    }
                }
                while (!queue.isEmpty()) {
                    System.out.println(queue.size());
                    requestData(queue.poll());
                }
            } catch (IOException | ClassNotFoundException e) {
                e.printStackTrace();
            }
        }
    }

    //    получаем (читаем) данные у клиента
    public void receiveData(SelectionKey key) throws IOException {
        System.out.println("IN RECEIVE");
        DataHolder data = (DataHolder) key.attachment();
        data.channel = (DatagramChannel) key.channel();
        data.channel.configureBlocking(false);
        data.getBuffer().clear();
        data.setClientAdr(data.channel.receive(data.getBuffer())); //получили данные у клиента и адрес клиента с которого они пришли
        try {
            receivedCommand = data.getReceivedCommand();
            System.out.println("Server received CommandNet Object! " + receivedCommand.getEnteredCommand()[0]);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        if (data.getClientAdr() != null) {
            key.interestOps(SelectionKey.OP_WRITE);
        }
        queue.add(data);
    }

    //    отправляем данные клиенту (отправляем результат выполненных команд)
    public void requestData(DataHolder dataHolder) throws IOException, ClassNotFoundException {
        dataHolder.getBuffer().flip();
        CommandNet receivedCommand = dataHolder.getReceivedCommand();
        Wrapper wrapper = new Wrapper();

        try (ByteArrayOutputStream out = new ByteArrayOutputStream();
             ObjectOutputStream oos = new ObjectOutputStream(out)) {
            Answer answer = new Executor().execute(wrapper.getWrappedCommand(receivedCommand));
            oos.writeObject(answer);
            byte[] b =out.toByteArray();
            ByteBuffer buff = ByteBuffer.wrap(b);
            dataHolder.channel.send(buff, dataHolder.getClientAdr());
        }
    }
}
