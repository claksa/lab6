package server.serverside;

import client.Reader;
import server.commands.Commandable;
import server.exceptions.NoSuchCommandException;
import server.lib.ServerCommands;

import java.util.List;
import java.util.Scanner;

public class ServerConsole implements Runnable {
    List<Commandable> serverList;
    private boolean isSuchCommand = false;

    public ServerConsole() {
        ServerCommands commands = new ServerCommands();
        serverList = commands.getServerCommands();
    }

    @Override
    public void run() {
        Scanner scanner = new Scanner(System.in);
        Reader.PrintMsg("server console started");
        String enteredCommand = null;
        while (Server.running) {
            try {
                enteredCommand = scanner.nextLine();
                if (enteredCommand.equals(" ")){
                    Reader.PrintMsg("You entered a space. Please wait for the processing of the command sent by the client (if any)");
                    continue;
                }
                executeServer(enteredCommand);
            } catch (NoSuchCommandException e) {
                Reader.PrintMsg("it is not a command.");
                Server.stop();
            }
        }
    }

    public void executeServer(String command) throws NoSuchCommandException {
        StringBuilder res = new StringBuilder();
        for (Commandable each : serverList) {
            if (command.equals(each.getName()) && command.equals("")) {
                each.execute(command);
                isSuchCommand = true;
            }
            if (command.equals("help")) {
                res.append(each.getName()).append(" - ").append(each.getDescription());
                Reader.PrintMsg(res.toString() + "help - get list about available server commands");
                isSuchCommand = true;
            }
        }
        if (!isSuchCommand) {
            throw new NoSuchCommandException();
        }
    }
}
