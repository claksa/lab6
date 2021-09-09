package server.commands;

import java.util.ArrayList;

public class Connect extends AbstractCommand {

    @Override
    public String getDescription() {
        return "connect client with server";
    }

    @Override
    public ArrayList<String> execute(String argument) {
        ArrayList<String> connectCommand = new ArrayList<>();
        connectCommand.add("connected");
        return connectCommand;
    }

    public boolean execute(){
        return true;
    }
}
