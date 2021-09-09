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
import java.util.Iterator;
import java.util.Set;

// объект класса управляет полученными данными от клиента; выполняет полученную команду и сразу отправляет результат
public class DataManager {
    Selector selector;
    CommandNet receivedCommand;

    public void manageData() {

        selector = Server.getSelector();
        while (Server.isRunning()) {
            try {
                selector.select();
                Set<SelectionKey> selectedKeys = selector.selectedKeys();
                Iterator<SelectionKey> keyIterator = selectedKeys.iterator();
                while (keyIterator.hasNext()) {
                    SelectionKey key = keyIterator.next();
                    if (key.isValid()) {
                        if (key.isReadable()) {
                            receiveData(key);
                        } else if (key.isWritable()) {
                            requestData(key);
                        }
                    }
                    keyIterator.remove();
                }
            } catch (IOException | ClassNotFoundException e) {
                e.printStackTrace();
            }
        }
    }

    //    получаем (читаем) данные у клиента
    public void receiveData(SelectionKey key) throws IOException {
        DatagramChannel channel = (DatagramChannel) key.channel();
        channel.configureBlocking(false);
        DataHolder data = (DataHolder) key.attachment();
        data.getBuffer().clear();
        data.setClientAdr(channel.receive(data.getBuffer())); //получили данные у клиента и адрес клиента с которого они пришли
        try {
            receivedCommand = data.getReceivedCommand();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        if (data.getClientAdr() != null) {
            key.interestOps(SelectionKey.OP_WRITE);
        }
    }

    //    отправляем данные клиенту (отправляем результат выполненных команд)
    public void requestData(SelectionKey key) throws IOException, ClassNotFoundException {
        DatagramChannel channel = (DatagramChannel) key.channel();
        channel.configureBlocking(false);
        DataHolder dataHolder = (DataHolder) key.attachment();
        dataHolder.getBuffer().flip();
        CommandNet receivedCommand = dataHolder.getReceivedCommand();
        Wrapper wrapper = new Wrapper();

        try (ByteArrayOutputStream out = new ByteArrayOutputStream();
             ObjectOutputStream oos = new ObjectOutputStream(out)) {
            Answer answer = new Executor().execute(wrapper.getWrappedCommand(receivedCommand));
            oos.writeObject(answer);
            byte[] b = new byte[65536];
            ByteBuffer buff = ByteBuffer.wrap(b);
            channel.send(buff, dataHolder.getClientAdr());
        }
    }
}
