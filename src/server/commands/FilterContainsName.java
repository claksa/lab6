package server.commands;

import server.exceptions.EmptyIOException;
import server.lib.CollectionManager;

import java.util.ArrayList;

public class FilterContainsName extends AbstractCommand {
    private final CollectionManager collectionManager;

    public FilterContainsName(CollectionManager collectionManager) {
        this.collectionManager = collectionManager;
    }

    @Override
    public ArrayList<String> execute(String argument) {
        ArrayList<String> filterContainsCommand = new ArrayList<>();
        if (argument.isEmpty()) {
            try {
                throw new EmptyIOException();
            } catch (EmptyIOException e) {
                filterContainsCommand.add("Error: you enter a null-argument");
            }
        }
        String list = collectionManager.containsSomeSubstring(argument);
        filterContainsCommand.add(list);

        if (list.isEmpty()){
            filterContainsCommand.add("Error: no such element\n");
        }
        filterContainsCommand.add("elements are displayed");
        return filterContainsCommand;
    }

    @Override
    public String getDescription() {
        return " (name) display elements whose name field value contains the given substring\n";
    }
}
