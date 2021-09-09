package server.commands;

import server.lib.CollectionManager;

import java.util.ArrayList;

public class Save extends AbstractCommand {
    private final CollectionManager collectionManager;

    public Save(CollectionManager collectionManager){
        this.collectionManager = collectionManager;
    }

    @Override
    public ArrayList<String> execute(String argument) {
        ArrayList<String> saveCommand = new ArrayList<>();
        collectionManager.save();
        saveCommand.add("saved\n");
        return saveCommand;
    }

    @Override
    public String getDescription() {
        return " save collection to file\n";
    }
}
