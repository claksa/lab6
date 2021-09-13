package server.commands;


import client.Validator;
import server.lib.CollectionManager;
import server.models.Ticket;

import java.io.Serializable;
import java.util.ArrayList;

public class Add extends AbstractCommand implements Serializable {
    private final CollectionManager collectionManager;
    private final Validator validator;



    public Add(CollectionManager collectionManager, Validator validator) {
        this.collectionManager = collectionManager;
        this.validator = validator;
    }


    @Override
    public ArrayList<String> execute(String argument, Ticket ticket, Integer id) {
        ArrayList<String> addCommand = new ArrayList<>();
        collectionManager.addItem(ticket);
        addCommand.add("the new item has been successfully added to the collection\n");
        return addCommand;
    }

    @Override
    public String getDescription() {
        return "add new element to collection\n";
    }
}
