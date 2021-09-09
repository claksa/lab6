package server.lib;

import client.CommandNet;
import server.commands.AbstractCommand;
import server.commands.Commandable;

import java.io.Serializable;
import java.util.List;

public class Wrapper implements Serializable {
    String command;
    String argument;


    public String getWrappedCommand(CommandNet cmd) {
        String[] command = cmd.getEnteredCommand();
        argument = command[1];
        for (Commandable each : CommanderHolder.getCmdList()) {
            if (!command[0].equals("")) {
                if (command[0].equals(each.getName())) {
                    this.command = command[0];
                }
            }
        }
        return this.command;
    }

//    важно: доступ только у клиента!
    String getCommand() {
        return command;
    }

    public String getArgument() {
        return argument;
    }

}
