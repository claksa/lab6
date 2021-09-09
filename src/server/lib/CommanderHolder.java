package server.lib;

import server.commands.Commandable;

import java.util.List;
import java.util.Scanner;

public class CommanderHolder {
    private static Commander commander;
    private static List<Commandable> cmdList;

    public CommanderHolder(){
        commander = new Commander();
        cmdList = commander.getCommandsList(new FileManager(),new Scanner(System.in));
    }

    public static Commander getCommander() {
        return commander;
    }

    public static List<Commandable> getCmdList() {
        return cmdList;
    }
}
