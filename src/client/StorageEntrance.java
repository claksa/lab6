package client;

import server.commands.Commandable;
import server.lib.FileManager;
import server.lib.StorageForCommands;

import java.util.List;
import java.util.Scanner;

public class StorageEntrance {
    List<Commandable> cmdList;
    StorageForCommands storage;

    public StorageEntrance(){
        storage = new StorageForCommands();
        cmdList = storage.getCommandsList(new FileManager(), new Scanner(System.in));
    }

    public List<Commandable> getCmdList() {
        return cmdList;
    }

    public StorageForCommands getStorage() {
        return storage;
    }
}
