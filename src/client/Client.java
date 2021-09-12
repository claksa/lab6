package client;

import server.commands.Commandable;
import server.lib.CommanderHolder;
import server.serverside.Answer;

import java.io.*;
import java.net.*;
import java.util.Scanner;

public class Client {
    InetAddress address;
    SocketAddress serverAdr = new InetSocketAddress("localhost", 9000);
    DatagramSocket socket;
    CommandNet commandNetNext = null;

    Scanner scanner;
    boolean isRunning = true;
    boolean isEstablishedConnectionWithServer = false;
    boolean isStarted = false;

    public Client() {
        Reader.PrintMsg("client started");
        CommanderHolder commander = new CommanderHolder();
        scanner = new Scanner(System.in);
        try {
            address = InetAddress.getByName("localhost");
        } catch (UnknownHostException e) {
            Reader.PrintErr("unknown host");
        }
    }

    //    стартовая точка входа в программу
    public void run() {
        try {
            while (true) {
                if (!isStarted) {
                    startClient();
                    continue;
                }
                String line = scanner.nextLine();
                String[] message = (line.trim() + " ").split(" ", 2);
                playConsole(message);
                CommandNet cmd = new CommandNet(message);
                System.out.println("the client starts sending the command to the server");
                send(cmd);
            }
        } catch (IOException e){
            System.out.println(" IO Exception");
            return;
        }
    }

    //   инициализирует работу клиента
    public void startClient() {
        if (isRunning && isStarted) {
            Reader.PrintMsg("the client has already connected to server");
        } else {
            connectServer();
            createThreadRespondent(); //работает пока isrunning
            isStarted = true;
        }
    }

    //    точка создания сокета и начало соединения с сервером
    public void connectServer() {
        try {
            socket = new DatagramSocket();
            Reader.PrintMsg("client connected to socket " + socket);
            socket.connect(serverAdr);
            String[] connect = {"connect", " "};
            CommandNet commandNet = new CommandNet(connect);
            System.out.println("the client starts sending 'connect' command to the server");
            send(commandNet);
        } catch (SocketException e) {
            Reader.PrintErr("socket connection");
        } catch (IOException e) {
            Reader.PrintErr("sending connect command");
        }
    }

    public void send(CommandNet command) throws IOException {
        if (!command.getEnteredCommand()[0].equals("connect")){
            commandNetNext = command;
        }
        try (ByteArrayOutputStream out = new ByteArrayOutputStream();
             ObjectOutputStream oos = new ObjectOutputStream(out);) {
            oos.writeObject(command);
            byte[] sendMessage = out.toByteArray();
            DatagramPacket packet = new DatagramPacket(sendMessage, sendMessage.length, address, 9000);
            socket.send(packet);
        }
        System.out.println("client send command to server");
    }

    //    обработчик ответов(=результаты выполненных команд) от сервера
    public void createThreadRespondent() {
        Thread respondent = new Thread(new Runnable() {
            @Override
            public void run() {
                while (true) {
                    try {
                        byte[] receivedMessage = new byte[65536];
                        DatagramPacket receivedPacket = new DatagramPacket(receivedMessage, receivedMessage.length);
                        socket.receive(receivedPacket);
                        byte[] received = receivedPacket.getData();
                        ByteArrayInputStream in = new ByteArrayInputStream(received);
                        ObjectInputStream ois = new ObjectInputStream(in);
                        Answer answer = (Answer) ois.readObject();
                        System.out.println("First message in answer:" + answer.getAnswer().get(0));
                        if (answer.getAnswer().get(0).equals("connected")) {
                            isEstablishedConnectionWithServer = true;
                            Reader.PrintMsg("the program was completed at the request of the user");
                            if (commandNetNext!=null){
                                System.out.println(" i try to send command [ "+ commandNetNext.getEnteredCommand()[0]+ " ] again!");
                                send(commandNetNext);
                            }
                        } else {
                            commandNetNext  = null;
                        }
                        answer.printAnswer();
                    } catch (PortUnreachableException e) {
                        Reader.PrintMsg("failed to connect to the server :( try after 3 seconds");
                        try {
                            Thread.sleep(1000);
                            System.out.println(".");
                            Thread.sleep(1000);
                            System.out.println("..");
                            Thread.sleep(1000);
                            System.out.println("...");
                            connectServer();
                        } catch (InterruptedException interruptedException) {
                            Reader.PrintMsg("girls, we were hacked");
                        }
                    } catch (ClassNotFoundException e) {
                        Reader.PrintMsg("THIS IS NOT A LEARNING ALARM!! CLASS NOT FOUND FOUND! I REPEAT, NO CLASS.\n" +
                                "HAPPY DAY CLIENT HURRAY!!!");
                        break;
                    } catch (IOException e) {
                        Reader.PrintMsg("you sell me some game. give me a normal IO");
                    }
                    System.out.println("LISTENING: ");
                }
            }
        });
        respondent.start();
    }

    //    запустить когда на клиенте будет введена команда работающая с коллекцией
    public void playConsole(String[] command) {
//        String[] command = (cmd.trim() + " ").split(" ", 2);
            if (!(command[0].trim().equals("") | command[1].trim().equals(""))) {
                for (Commandable eachCommand : CommanderHolder.getCmdList()) {
                    if (command[0].trim().equals(eachCommand.getName())) {
                        if (!command[0].trim().equals("connect")) {

                            if (command[0].trim().equals("add") | command[0].trim().equals("update")) {
                                CommanderHolder.getCommander().getValidator().setId();
                            }
                            CommanderHolder.getCommander().getValidator().setTicket();
                        }
                    }
                }
            }
    }


}
