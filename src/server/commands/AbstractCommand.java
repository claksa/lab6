package server.commands;

import client.Client;
import client.CommandNet;
import server.lib.StorageEntrance;

import java.io.Serializable;

import static client.Reader.PrintMsg;


public  abstract class AbstractCommand implements Commandable, Serializable {
    private boolean isCommand;

    public boolean isCommand(String[] cmd) {
        if (!cmd[0].trim().equals("")) {
            for (Commandable eachCommand : new StorageEntrance().getCmdList()) {
                if (cmd[0].trim().equals(eachCommand.getName())) {
                    PrintMsg("you entered a right command");
                    isCommand = true;
                    break;
                } else {
                    isCommand = false;
                }
            }
        }
        return isCommand;
    }

    public boolean isCommand() {
        return isCommand;
    }

}
