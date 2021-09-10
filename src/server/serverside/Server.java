package server.serverside;

import client.Reader;
import server.commands.Commandable;
import server.commands.Executor;
import server.lib.Commander;
import server.lib.CommanderHolder;
import server.lib.FileManager;
import server.lib.StorageEntrance;

import java.io.IOException;
import java.net.DatagramSocket;
import java.net.InetSocketAddress;
import java.net.SocketAddress;
import java.nio.channels.DatagramChannel;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Server {
    DatagramChannel channel;
    static Selector selector;
    public static boolean running = false;

    public Server(){
        CommanderHolder commander = new CommanderHolder();
    }

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
