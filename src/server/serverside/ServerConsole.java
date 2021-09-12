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
        try (Scanner scanner = new Scanner(System.in)) {
            String message = "server console started\n" +
                    "if you want to know about the commands available for execution on the server, enter 'help'\n" +
                    "or you can just wait for client connection";
            System.out.println(message);
            while (Server.running) {
                try {
                    String enteredCommand = scanner.nextLine();
                    executeServer(enteredCommand);
                } catch (NoSuchCommandException e) {
                    Reader.PrintMsg("it is not a command.");
                    Server.stop();
                }
            }
        }
    }

    public void executeServer(String command) throws NoSuchCommandException {
        StringBuilder res = new StringBuilder(" ");
        if (command.trim().equals("help")) {
            res.append("\nsave - save collection to file\n");
            res.append("exit - end the server\n");
            res.append("help - get list about available server commands\n");
            System.out.println(res);
            isSuchCommand = true;
        }
        for (Commandable each : serverList) {
            if (command.equals(each.getName())) {
                each.execute(command);
                isSuchCommand = true;
            }
        }
        if (!isSuchCommand) {
            throw new NoSuchCommandException();
        }
    }
}
