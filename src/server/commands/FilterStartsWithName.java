package server.commands;

import server.exceptions.EmptyIOException;
import server.lib.CollectionManager;

import java.io.Serializable;
import java.util.ArrayList;

public class FilterStartsWithName implements Commandable{
    private final CollectionManager collectionManager;

    public FilterStartsWithName(CollectionManager collectionManager) {
        this.collectionManager = collectionManager;
    }

    /**
     * execute filter_starts_with_name command
     * @param argument name to check
     */

    @Override
    public ArrayList<String> execute(String argument) {
        ArrayList<String> filterStartsCommand = new ArrayList<>();
        if (argument.isEmpty()) {
            try {
                throw new EmptyIOException();
            } catch (EmptyIOException e) {
                filterStartsCommand.add("Error: you enter null-argument");
            }
        }
        String list = collectionManager.startsWithSubstring(argument);
        filterStartsCommand.add(list);
        if (list.isEmpty()) {
            filterStartsCommand.add("Error: no such element");
        }
        return filterStartsCommand;
    }

    @Override
    public String getDescription() {
        return " (name) display elements whose name field value begins with a given substring\n";
    }
}
