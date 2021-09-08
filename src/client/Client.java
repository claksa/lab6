package client;

import server.commands.Commandable;
import server.serverside.Answer;

import java.io.*;
import java.net.*;
import java.util.Scanner;


public class Client {
    InetAddress address;
    SocketAddress serverAdr = new InetSocketAddress("localhost", 9000);
    DatagramSocket socket;
    public static Wrapper wrapper;
    Scanner scanner;
    boolean isConnected = true;
    boolean isEstablishedConnectionWithServer;
    boolean isStarted = false;

    public Client() {
        Reader.PrintMsg("client started");
        wrapper = new Wrapper();
        scanner = new Scanner(System.in);
        try {
            address = InetAddress.getByName("localhost");
        } catch (UnknownHostException e) {
            Reader.PrintErr("unknown host");
        }
    }

    //    стартовая точка входа в программу
    public void run() {
        while (isConnected) {
            if (!isStarted) {
                startClient();
                Reader.PrintMsg("client was successfully connected");
                continue;
            }
            String message = scanner.nextLine();
            playConsole(message);
            send(wrapper.getWrappedCommand(message));
        }
    }

    //   инициализирует работу клиента
    public boolean startClient() {
        if (isConnected && isStarted) {
            Reader.PrintMsg("the client has already connected to server");
        } else {
            connectServer();
            createThreadRespondent();
            isConnected = true;
            isStarted = true;
        }
        return isConnected;
    }

    //    точка создания сокета и начало соединения с сервером
    public void connectServer() {
        try {
            socket = new DatagramSocket();
            Reader.PrintMsg("client connected to socket " + socket);
            socket.connect(serverAdr);
            String connect = "connect";
            send(wrapper.getWrappedCommand(connect));
            isConnected = true;
        } catch (SocketException e) {
            Reader.PrintErr("socket connection");
            isConnected = false;
        }
    }

    public void send(Commandable object) {
        try (ByteArrayOutputStream out = new ByteArrayOutputStream();
             ObjectOutputStream oos = new ObjectOutputStream(out);) {
            oos.writeObject(object);
            byte[] sendMessage = out.toByteArray();
            DatagramPacket packet = new DatagramPacket(sendMessage, sendMessage.length, address, 9000);
            socket.send(packet);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    //    обработчик ответов(=результаты выполненных команд) от сервера
    public void createThreadRespondent() {
        Thread respondent = new Thread(new Runnable() {
            @Override
            public void run() {
                while (isConnected()) {
                    try {
                        byte[] receivedMessage = new byte[65536];
                        DatagramPacket receivedPacket = new DatagramPacket(receivedMessage, receivedMessage.length);
                        socket.receive(receivedPacket);
                        byte[] received = receivedPacket.getData();
                        ByteArrayInputStream in = new ByteArrayInputStream(received);
                        ObjectInputStream ois = new ObjectInputStream(in);
                        Answer answer = (Answer) ois.readObject();
                        if (answer.getAnswer().get(0).equals("connected")) {
                            isEstablishedConnectionWithServer = true;
                            Reader.PrintMsg("the program was completed at the request of the user");
                        }
                        answer.printAnswer();
                    } catch (IOException | ClassNotFoundException e) {
                        e.printStackTrace();
                    }
                }
            }
        });
    }

    //    запустить когда на клиенте будет введена команда работающая с коллекцией
    public void playConsole(String cmd) {
        String[] command = (cmd.trim() + " ").split(" ", 2);
        if (!(command[0].trim().equals("") | command[1].trim().equals(""))) {
            for (Commandable eachCommand : wrapper.getCmdList()) {
                if (command[0].trim().equals(eachCommand.getName())) {
                    if (!command[0].trim().equals("connect")) {

                        if (command[0].trim().equals("add") | command[0].trim().equals("update")) {
                            wrapper.getStorage().getValidator().setId();
                        }
                        wrapper.getStorage().getValidator().setTicket();
                    }
                }
            }
        }
    }

    boolean isConnected() {
        return isConnected;
    }

    public static Wrapper getWrapper() {
        return wrapper;
    }
}
