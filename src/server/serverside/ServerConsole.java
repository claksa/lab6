package server.serverside;

import client.Reader;
import server.commands.Commandable;
import server.exceptions.NoSuchCommandException;
import server.lib.ServerCommands;

import java.util.List;
import java.util.Scanner;

public class ServerConsole implements Runnable{
    List<Commandable> serverList;
    private boolean isSuchCommand = false;

    public ServerConsole(){
        ServerCommands commands = new ServerCommands();
        serverList = commands.getServerCommands();
    }

    @Override
    public void run() {
        Scanner scanner = new Scanner(System.in);
        Reader.PrintMsg("server console started.\nPlease, enter help to read about available commands");
        while (Server.isRunning()) {
            try {
                executeServer(scanner.nextLine());
            } catch (NoSuchCommandException e) {
                Reader.PrintErr("it is not right command. Please? enter help to get available server commands");
//                or stop server
            }

        }
    }

    public void executeServer(String command) throws NoSuchCommandException {
        for (Commandable each: serverList){
            if (command.equals(each.getName())){
                each.execute(command);
                isSuchCommand = true;
            } if (command.equals("help")) {
                Reader.PrintMsg(each.getDescription() + "\nhelp: get list about available server commands");
                isSuchCommand = true;
            }
        }
        if (!isSuchCommand){
            throw new NoSuchCommandException();
        }
    }
}
