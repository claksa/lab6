package client;

import server.lib.StorageEntrance;

import javax.print.attribute.standard.PrinterResolution;
import java.io.IOException;
import java.net.UnknownHostException;

public class ClientPoint {
    public static void main(String[] args) {
        Client client = new Client();
        client.run();
    }
}
