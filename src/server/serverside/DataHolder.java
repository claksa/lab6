package server.serverside;

import client.CommandNet;
import client.Reader;
import server.commands.Commandable;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.net.SocketAddress;
import java.nio.ByteBuffer;

// объект класса хранит полученные данные (команды)  от клиента и немножко контролирует подключение
public class DataHolder {
    public boolean isEstablishedConnection = false;
    private final byte[] b = new byte[65536];
    private SocketAddress clientAdr;
    ByteBuffer buffer = ByteBuffer.wrap(b);

   public CommandNet getReceivedCommand() throws ClassNotFoundException {
       CommandNet command = null;
       try(ByteArrayInputStream in = new ByteArrayInputStream(b);
           ObjectInputStream ois = new ObjectInputStream(in)){
           command = (CommandNet) ois.readObject();
           System.out.println(" Server received CommandNet Object!");
           if (command.getEnteredCommand()[0].equals("connect")){
               isEstablishedConnection = true;
           }

       } catch (IOException e) {
           Reader.PrintErr("problems with receiving data");
       }
       return command;
   }

    public boolean isEstablishedConnection() {
        return isEstablishedConnection;
    }

    public ByteBuffer getBuffer(){
       return buffer;
    }

    public SocketAddress getClientAdr() {
        return clientAdr;
    }

    public void setClientAdr(SocketAddress clientAdr) {
        this.clientAdr = clientAdr;
    }
}
