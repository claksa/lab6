package client;

import server.exceptions.NoSuchCommandException;

import java.io.*;
import java.net.*;
import java.util.Scanner;


/**
 * client request handler
 */
public class RequestHandler {
    public static final int PORT = 3345;
    DatagramPacket packet;
    Scanner scanner;
    InetAddress address;

    public RequestHandler(Scanner scanner) throws UnknownHostException {
        this.scanner = scanner;
        address = InetAddress.getByName("localhost");
    }

    public void send(DatagramSocket socket) {
        try (ByteArrayOutputStream byteStream = new ByteArrayOutputStream();
             ObjectOutputStream oos = new ObjectOutputStream(byteStream);) {

            Reader.PrintMsg("Client (IPAddress: " + address + ", port: " + PORT + ") connected to socket ");
//            oos.writeObject(new ConsoleManager(scanner).getCommand());

            byte[] buf = byteStream.toByteArray();
            packet = new DatagramPacket(buf, buf.length, address, PORT);
            socket.send(packet);
        } catch (IOException e) {
            Reader.PrintErr("sending request");
        }
    }
}
//        } catch (NoSuchCommandException e) {
//            Reader.PrintErr(" no such command.\nPlease, enter help");
//        }



