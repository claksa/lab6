package client;

import server.commands.Commandable;
import server.lib.FileManager;
import server.lib.Commander;

import java.io.*;
import java.net.*;
import java.nio.charset.StandardCharsets;
import java.util.*;

import static client.Reader.*;

public class ClientManager {
    Scanner scanner;
    InetAddress address;
    String message;
    List<Commandable> list;
    Commander commands;


    public ClientManager() {
        Reader.PrintMsg("client started");
        scanner = new Scanner(System.in);
        commands = new Commander();
        list = commands.getCommandsList(new FileManager(),scanner);

        try {
            address = InetAddress.getByName("localhost");
        } catch (UnknownHostException e) {
            PrintErr("unknown host");
        }
    }


    public void startClient() throws IOException {
        DatagramSocket socket = null;

        try {
            socket = new DatagramSocket();
            PrintMsg("Client (IPAddress: " + address + ") connected to socket");
            PrintMsg("Hello! Please, enter help to get info about available commands!");
            while (true) {
                PrintMsg("Enter a command here: ");
//                message = scanner.nextLine();
//                Commandable commandable = (Commandable) scanner.nextLine();

                if (message.equals("quit")) {
                    System.exit(0);
                }

//                sendRequest(socket, message);
                socket.setSoTimeout(20000);
                receiveAnswer(socket);
            }
        } catch (SocketException e) {
            PrintErr("[there is no opened sockets] or [timeout reached " + e + " ]");
        } catch (IOException e) {
            PrintErr("[the response from the client is not available on the server]");
        } catch (ClassNotFoundException e) {
            PrintErr("[problems getting a response from the server]");
        }
    }

    /*public void sendRequest(DatagramSocket socket, Commandable command) {
        try (ByteArrayOutputStream byteStream = new ByteArrayOutputStream();
             ObjectOutputStream oos = new ObjectOutputStream(byteStream);) {
            oos.writeObject(command);
            byte[] sendMessage = byteStream.toByteArray();
            DatagramPacket packet = new DatagramPacket(sendMessage, sendMessage.length, address, 9000);
            socket.send(packet);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }*/

    public void receiveAnswer(DatagramSocket socket) throws IOException, ClassNotFoundException {
        byte[] receivedMessage = new byte[65536];
        DatagramPacket receivedPacket = new DatagramPacket(receivedMessage, receivedMessage.length);
        socket.receive(receivedPacket);
        byte[] received = receivedPacket.getData();
        try (ByteArrayInputStream in = new ByteArrayInputStream(received);
             ObjectInputStream ois = new ObjectInputStream(in);) {
            ois.readObject();
            String receivedMsg = new String(received, StandardCharsets.UTF_8).trim();
            PrintMsg("received from server: [" + Arrays.toString(received) + "]\n from " + receivedPacket.getSocketAddress());
        }
    }



    public void receiveInfo(DatagramSocket socket) throws IOException {
        byte[] info = new byte[1024];
        DatagramPacket datagramPacket = new DatagramPacket(info, info.length);
        socket.receive(datagramPacket);
        String inf = new String(datagramPacket.getData(), StandardCharsets.UTF_8).trim();
        PrintMsg("received from client information [" + inf + "]\n from " + datagramPacket.getSocketAddress());
    }

}
