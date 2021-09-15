package server.commands;

import client.Validator;
import server.exceptions.EmptyIOException;
import server.exceptions.NoSuchIdException;
import server.lib.CollectionManager;
import server.models.Ticket;

import java.util.ArrayList;

public class Remove extends AbstractCommand {
    private final CollectionManager collectionManager;
    private final Validator validator;

    public Remove(CollectionManager collectionManager, Validator validator) {
        this.collectionManager = collectionManager;
        this.validator = validator;
    }


    @Override
    public ArrayList<String> execute(String argument, Ticket ticket, Integer id) {
        ArrayList<String> removeCommand = new ArrayList<>();
        try {
            if (argument.trim().isEmpty()) throw new EmptyIOException();
            if (collectionManager.isEqualId(id)) throw new NoSuchIdException();
        } catch (EmptyIOException e) {
            removeCommand.add("Error: you entered a null-argument");
        } catch (NoSuchIdException e) {
            removeCommand.add("Error: no element with such id in collection");
        }
        collectionManager.remove(id);
        removeCommand.add("removed\n");
        return removeCommand;
    }

    @Override
    public String getDescription() {
        return " (id) remove an item from the collection by its id\n";
    }
}
