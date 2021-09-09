package server.commands;

import client.Validator;
import server.exceptions.EmptyIOException;
import server.exceptions.NoSuchIdException;
import server.lib.CollectionManager;
import server.models.Ticket;

import java.util.ArrayList;

public class Update extends AbstractCommand {
    private final CollectionManager collectionManager;
    private final Validator validator;

    public Update(CollectionManager collectionManager, Validator validator) {
        this.collectionManager = collectionManager;
        this.validator = validator;
    }

    @Override
    public ArrayList<String> execute(String argument) {
        int id;
        ArrayList<String> updateCommand = new ArrayList<>();
        try {
            if (argument.trim().equals("")) throw new EmptyIOException();
            id = Integer.parseInt(argument.trim());
            if (!collectionManager.isEqualId(id)) throw new NoSuchIdException();
        } catch (NumberFormatException e) {
            updateCommand.add("Error: you enter not number value");
        } catch (EmptyIOException e) {
            updateCommand.add("Error: you enter null-argument");
        } catch (NoSuchIdException e) {
            updateCommand.add("Error: No such ID in collection");
        }
        Ticket ticket = validator.getTicket();
        collectionManager.update(ticket);
        updateCommand.add("updated\n");
        return updateCommand;
    }

    @Override
    public String getDescription() {
        return " (id) update the value of the collection element whose id is equal to the given\n";
    }
}
