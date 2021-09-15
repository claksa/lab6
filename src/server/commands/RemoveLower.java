package server.commands;

import client.Validator;
import server.lib.CollectionManager;
import server.models.Ticket;

import java.util.ArrayList;

public class RemoveLower extends AbstractCommand {
    private final CollectionManager collectionManager;

    public RemoveLower(CollectionManager collectionManager, Validator validator) {
        this.collectionManager = collectionManager;
    }

    @Override
    public ArrayList<String> execute(String argument, Ticket ticket, Integer id) {
        ArrayList<String> removeLowerCommand = new ArrayList<>();
        if (collectionManager.isEqualId(id)) {
            ticket.setId(id);
            if(collectionManager.removeIfLowerId(ticket)) {
                removeLowerCommand.add("removed\n");
            } else {
                removeLowerCommand.add("Theres is no elements to remove");
            }
        } else {
            removeLowerCommand.add("I can not delete this ID. Please, enter unique");
        }
        return removeLowerCommand;
    }


    @Override
    public String getDescription() {
        return " remove all elements from the collection that are less than the given one\n";
    }
}
