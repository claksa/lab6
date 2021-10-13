package mainlib;

import commands.Commandable;

import java.util.List;

public class CommanderHolder {
    private static final Commander commander = new Commander();
    private static final List<Commandable> cmdList = commander.getCommandsList(new FileManager());

    public static Commander getCommander() {
        return commander;
    }

    public static List<Commandable> getCmdList() {
        return cmdList;
    }


}
