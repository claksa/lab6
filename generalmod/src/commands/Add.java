package commands;

import mainlib.CollectionManager;
import models.Ticket;

import java.io.Serializable;
import java.util.ArrayList;

public class Add extends AbstractCommand implements Serializable {
    private final CollectionManager collectionManager;

    public Add(CollectionManager collectionManager) {
        this.collectionManager = collectionManager;
    }

    @Override
    public ArrayList<String> execute(String argument, Ticket ticket, Integer id) {
        ArrayList<String> addCommand = new ArrayList<>();
            if(collectionManager.addItem(ticket)) {
                addCommand.add("the new item added to the collection\n");
                collectionManager.setNeedToSort(true);
                return addCommand;
        } else {
                addCommand.add(" error in adding");
            }
        return addCommand;
    }

    @Override
    public String getDescription() {
        return "add new element to collection\n";
    }
}
