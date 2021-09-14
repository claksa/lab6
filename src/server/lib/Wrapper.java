package server.lib;

import client.Client;
import client.CommandNet;
import client.TicketInfoHolder;
import server.commands.Commandable;
import server.models.Ticket;

import java.io.Serializable;


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

    public Ticket getWrappedTicket(CommandNet cmd) {
        System.out.println("IN GET WRAPPED TICKET (BEFORE EXECUTION): ");
        System.out.println(cmd.getTicket());
        return cmd.getTicket();
    }

    public Integer getWrappedId(CommandNet cmd){
        System.out.println("IN GET WRAPPED ID (BEFORE EXECUTION):");
        return cmd.getId();
    }

    public String getArgument() {
        return argument;
    }

}
