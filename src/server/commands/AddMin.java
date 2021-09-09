package server.commands;

import client.Validator;
import server.lib.CollectionManager;
import server.models.Ticket;

import java.util.ArrayList;

public class AddMin extends AbstractCommand {
    private final CollectionManager collectionManager;
    private final Validator validator;

    public AddMin(CollectionManager collectionManager,Validator validator) {
        this.collectionManager = collectionManager;
        this.validator = validator;
    }

    @Override
    public ArrayList<String> execute(String argument) {
        Integer id = validator.getId();
        Ticket object = validator.getTicket();
        ArrayList<String> addMinCommand = new ArrayList<>();
        if (!collectionManager.isEqualId(id)) {
            collectionManager.addMin(object);
            addMinCommand.add("min item added\n");
        } else addMinCommand.add("such an ID already exists, alas");
        return addMinCommand;
    }

    @Override
    public String getDescription() {
        return " add a new item to a collection if its value is less than the smallest item in this collection\n";
    }
}
