package server.commands;

import server.lib.CollectionManager;

import java.util.ArrayList;

public class Info extends AbstractCommand {
    private final CollectionManager collectionManager;

    public Info(CollectionManager collectionManager){
        this.collectionManager = collectionManager;
    }

    @Override
    public ArrayList<String> execute(String argument) {
        ArrayList<String> infoCommand = new ArrayList<>();
        infoCommand.add(collectionManager.getInformation());
        infoCommand.add("\ninformation received");
        return infoCommand;
    }

    @Override
    public String getDescription() {
        return " print information about the collection to the stdout: type, date of initialization, number of elements\n";
    }
}
