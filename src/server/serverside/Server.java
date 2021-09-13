package server.serverside;

import client.Reader;
import server.lib.CommanderHolder;

import java.io.IOException;
import java.net.*;
import java.nio.channels.DatagramChannel;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;

public class Server {
    DatagramChannel channel;
    static Selector selector;
    public static boolean running = false;


    public void startServer() {
        Reader.PrintMsg("server started");
        try {
            running = true;
            channel = DatagramChannel.open();
            channel.configureBlocking(false);
            DatagramSocket server = channel.socket();
            selector = Selector.open();
            SocketAddress socketAddress = new InetSocketAddress(9000);
            server.bind(socketAddress);
            Thread console = new Thread(new ServerConsole());
            console.start();
            channel.register(selector, SelectionKey.OP_READ, new DataHolder());
            new DataManager().manageData();
        } catch (IOException e) {
            Reader.PrintErr("channel wasn't open");
        }
    }


    public static Selector getSelector() {
        return selector;
    }

    public static void stop(){
        running = false;
    }

}
