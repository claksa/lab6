package client;

import server.commands.Commandable;
import server.lib.FileManager;
import server.lib.StorageForCommands;

import java.io.Serializable;
import java.util.List;
import java.util.Scanner;

public class Wrapper implements Serializable {
    Commandable command;
    String argument;

    List<Commandable> cmdList;
    StorageForCommands storage;

    public Wrapper() {
        storage = new StorageForCommands();
        cmdList = storage.getCommandsList(new FileManager(), new Scanner(System.in));
    }

//    важно: доступ только у клиента!
    Commandable getWrappedCommand(String cmd) {
        String[] command = (cmd.trim() + " ").split(" ", 2);
        argument = command[1];
        for (Commandable each : cmdList) {
            if (!command[0].equals("")) {
                if (command[0].equals(each.getName()) | command[0].equals("execute_script")) {
                    this.command = each;
                }
            }
        }
        return this.command;
    }

//    важно: доступ только у клиента!
    Commandable getCommand() {
        return command;
    }

    public String getArgument() {
        return argument;
    }

    public List<Commandable> getCmdList() {
        return cmdList;
    }

    public StorageForCommands getStorage() {
        return storage;
    }
}
