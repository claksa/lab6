package server.commands;

import server.lib.CollectionManager;

import java.io.Serializable;
import java.util.ArrayList;

public class Clear implements Commandable {
    private final CollectionManager collectionManager;

    public Clear(CollectionManager collectionManager) {
        this.collectionManager = collectionManager;
    }



    @Override
    public ArrayList<String> execute(String argument) {
        collectionManager.getTickets().clear();
        ArrayList<String> clearCommand = new ArrayList<>();
        clearCommand.add("collection cleaned successfully");
        return clearCommand;
    }

    @Override
    public String getDescription() {
        return " clear collection\n";
    }
}
