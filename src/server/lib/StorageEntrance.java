package server.lib;

import server.commands.Commandable;

import java.util.List;
import java.util.Scanner;

public class StorageEntrance {
    private final List<Commandable> cmdList;
    private final Commander storage;

    public StorageEntrance() {
        storage = new Commander();
        cmdList = storage.getCommandsList(new FileManager(),new Scanner(System.in));
    }

    public List<Commandable> getCmdList() {
        return cmdList;
    }

    public Commander getStorage() {
        return storage;
    }
}
