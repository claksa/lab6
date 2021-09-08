package server.serverside;

import client.Reader;
import bridge.Message;
import server.commands.Commandable;
import server.commands.Executor;
import server.lib.FileManager;
import server.lib.StorageForCommands;

import java.io.*;
import java.net.*;
import java.nio.*;
import java.nio.channels.DatagramChannel;
import java.nio.charset.StandardCharsets;
import java.util.*;

import static client.Reader.PrintMsg;

public class ServerManager implements Runnable {
    DatagramChannel channel;
    SocketAddress client;
    Message answer;
    Executor executor;


    public ServerManager() {


        executor = new Executor();

        PrintMsg("server started");
        try {
            channel = DatagramChannel.open();
            DatagramSocket socket = channel.socket();
            SocketAddress address = new InetSocketAddress(9000);
            socket.bind(address);
            channel.configureBlocking(false);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void run() {
        try {
            handleReceive();
        } catch (IOException | ClassNotFoundException e) {
            Reader.PrintErr("problem in receiving buffer using channel");
        }
    }

    public void manageData() throws IOException {
        boolean running = true;
        ByteBuffer buffer = ByteBuffer.allocateDirect(65536);
        while (running) {
            client = channel.receive(buffer);
            buffer.flip();
            buffer.mark();
            byte[] b = new byte[buffer.remaining()];
            buffer.get(b);

            try (ByteArrayInputStream in = new ByteArrayInputStream(b);
                 ObjectInputStream ois = new ObjectInputStream(in);
                 ByteArrayOutputStream out = new ByteArrayOutputStream();
                 ObjectOutputStream oos = new ObjectOutputStream(out);) {

                String receivedData = "";
                receivedData = (String) ois.readObject();
                PrintMsg("received message: " + receivedData);

                ByteBuffer toClient = ByteBuffer.wrap(out.toByteArray());
                oos.writeObject(executor.execute(receivedData));
                channel.send(toClient, client);
                toClient.clear();
                buffer.reset();
                if (receivedData.equals("exit")) {
                    running = false;
                }
            } catch (ClassNotFoundException | IOException e) {
                e.printStackTrace();
            }

        }
    }

    public void handleReceive() throws IOException, ClassNotFoundException {
        boolean running = true;
        while (running){
            byte[] b = new byte[65634];
            ByteBuffer buffer = ByteBuffer.wrap(b);
            buffer.clear();
            client = channel.receive(buffer);
            ByteArrayInputStream in = new ByteArrayInputStream(buffer.array());
            ObjectInputStream ois = new ObjectInputStream(in);
            String command = "";
            command = (String) ois.readObject();
            PrintMsg("received command: " + command);

            if (command.equals("quit")){
                running = false;
            }
        }
    }

    public void sendInfo() throws IOException {
        byte[] b = answer.getMessage().getBytes(StandardCharsets.UTF_8);
        ByteBuffer buf = ByteBuffer.wrap(b);
        channel.send(buf, client);
    }

    public void send() throws IOException {
        try (ByteArrayOutputStream out = new ByteArrayOutputStream();
             ObjectOutputStream oos = new ObjectOutputStream(out);) {
            oos.writeObject(answer.getMessage());
            ByteBuffer buffer = ByteBuffer.wrap(out.toByteArray());
            channel.send(buffer, client);
            buffer.clear();
        }
    }
}



