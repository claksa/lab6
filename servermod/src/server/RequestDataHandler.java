package server;

import commands.Executor;
import lib.Wrapper;
import mainlib.Answer;
import mainlib.CommandNet;
import models.Ticket;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.util.logging.Logger;

public class RequestDataHandler {
    private static final Logger log = Logger.getLogger(ReceiveDataHandler.class.getName());


    public static void requestData() {

        try (ByteArrayOutputStream out = new ByteArrayOutputStream();
             ObjectOutputStream oos = new ObjectOutputStream(out)) {

            DataHolder dataHolder = DataManager.queue.poll();

            if (dataHolder == null){
                System.out.println("Error: there is no way to get a command!");
                throw new ClassNotFoundException();
            }

            dataHolder.getBuffer().flip();

            CommandNet receivedCommand = dataHolder.getReceivedCommand();
            Wrapper wrapper = new Wrapper();

            String command = wrapper.getWrappedCommand(receivedCommand);
            String argument = wrapper.getArgument();
            Ticket tick = wrapper.getWrappedTicket(receivedCommand);
            Integer id = wrapper.getWrappedId(receivedCommand);
            Answer answer = new Executor().execute(command, argument, tick, id);
            oos.writeObject(answer);
            byte[] b = out.toByteArray();
            ByteBuffer buff = ByteBuffer.wrap(b);
            dataHolder.channel.send(buff, dataHolder.getClientAdr());
            log.info("send answer " + b.length + " bytes");
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
        if (DataManager.key != null) {
            DataManager.key.interestOps(SelectionKey.OP_READ);
        }
    }
}
