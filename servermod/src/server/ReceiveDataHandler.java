package server;

import mainlib.CommandNet;

import java.io.IOException;
import java.nio.channels.DatagramChannel;
import java.nio.channels.SelectionKey;
import java.util.logging.Level;
import java.util.logging.Logger;

public class ReceiveDataHandler {
    private static final Logger log = Logger.getLogger(ReceiveDataHandler.class.getName());


    public static void receive() {
        DataHolder data = (DataHolder) DataManager.key.attachment();
        try {
            data.channel = (DatagramChannel) DataManager.key.channel();
            data.channel.configureBlocking(false);
            data.getBuffer().clear();
            data.setClientAdr(data.channel.receive(data.getBuffer()));
            CommandNet receivedCommand = data.getReceivedCommand();
            log.info("the server received the command from the client: " + receivedCommand.getEnteredCommand()[0]);
        } catch (IOException | ClassNotFoundException e) {
            log.log(Level.SEVERE, "error in configure blocking or data receiving");
        }
        if (data.getClientAdr() != null) {
            DataManager.key.interestOps(SelectionKey.OP_WRITE);
        }
        DataManager.queue.add(data);
    }

}
