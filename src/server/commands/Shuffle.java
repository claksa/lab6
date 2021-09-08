package server.commands;

import server.lib.CollectionManager;

import java.util.ArrayList;
import java.util.Collections;

public class Shuffle implements Commandable {
    private final CollectionManager collectionManager;

    public Shuffle(CollectionManager collectionManager) {
        this.collectionManager = collectionManager;
    }

    @Override
    public ArrayList<String> execute(String argument) {
        ArrayList<String> shuffleCommand = new ArrayList<>();
        Collections.shuffle(collectionManager.getTickets());
        shuffleCommand.add("shuffled\n");
        return shuffleCommand;
    }

    @Override
    public String getDescription() {
        return " shuffle the elements of the collection at random\n";
    }
}
