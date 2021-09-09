package client;

import server.commands.AbstractCommand;
import server.commands.Commandable;
import server.lib.FileManager;
import server.lib.StorageForCommands;

import java.io.Serializable;
import java.util.List;
import java.util.Scanner;

public class Wrapper implements Serializable {
    AbstractCommand command;
    String argument;


    public Wrapper() {
    }

//    надо получить из CommandNet AbstractCommand для выполнения
//    из CommandNet вытащить массив из аргумента и команды
//    важно: доступ только у клиента!
    public AbstractCommand getWrappedCommand(CommandNet cmd) {
        String[] command = cmd.getEnteredCommand();
        argument = command[1];
        for (Commandable each : Client.entrance.getCmdList()) {
            if (!command[0].equals("")) {
                if (command[0].equals(each.getName()) | command[0].equals("execute_script")) {
                    this.command = (AbstractCommand) each;
                }
            }
        }
        return this.command;
    }

//    важно: доступ только у клиента!
    AbstractCommand getCommand() {
        return command;
    }

    public String getArgument() {
        return argument;
    }

}
