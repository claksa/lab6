package server.commands;

import client.Client;
import client.CommandNet;

import java.io.Serializable;

import static client.Reader.PrintMsg;


public  abstract class AbstractCommand implements Commandable, Serializable {
    private boolean isCommand;

    public boolean isCommand(String[] cmd) {
        if (!cmd[0].trim().equals("")) {
            for (Commandable eachCommand : Client.getEntrance().getCmdList()) {
                if (cmd[0].trim().equals(eachCommand.getName()) | cmd[0].trim().equals("execute_script")) {
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
