package server.lib;

import server.commands.Commandable;
import server.commands.Exit;
import server.commands.Save;

import java.util.ArrayList;
import java.util.List;

public class ServerCommands {
    List<Commandable> serverCommands;

    public ServerCommands(){
        serverCommands = new ArrayList<Commandable>();
    }

    public List<Commandable> getServerCommands() {
        CollectionManager collectionManager = CommanderHolder.getCommander().collectionManager;
        serverCommands.add(new Save(collectionManager));
        serverCommands.add(new Exit(collectionManager));
        return serverCommands;
    }
}
