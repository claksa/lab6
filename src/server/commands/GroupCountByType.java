package server.commands;

import server.lib.CollectionManager;

import java.io.Serializable;
import java.util.ArrayList;

public class GroupCountByType implements Commandable {
    private final CollectionManager collectionManager;


    public GroupCountByType (CollectionManager collectionManager){
        this.collectionManager = collectionManager;
    }

    /**
     * execute group_count_by_type command
     * @param argument ticket type as string
     */

    @Override
    public ArrayList<String> execute(String argument) {
        collectionManager.assort();
        ArrayList<String> groupCommand = new ArrayList<>();
        groupCommand.add("group_count_by_type executed\n");
        return groupCommand;
    }

    @Override
    public String getDescription() {
        return " group the elements of the collection by the value of the type field, display the number of elements in the group\n";
    }
}
