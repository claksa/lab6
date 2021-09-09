package server.commands;

import server.exceptions.LackOfAccessException;
import server.exceptions.NoSuchCommandException;
import server.lib.StorageEntrance;

import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;

public class ExecuteScript extends AbstractCommand {
    Executor executor;
    static ArrayList<String> executeScriptCommand;

    public ExecuteScript(){
        executor = new Executor();
    }

    @Override
    public String getDescription() {
        return "executes a script of the available commands";
    }

    @Override
    public ArrayList<String> execute(String argument) {
        executeScriptCommand = new ArrayList<>();
        try {
            if (!Files.isExecutable(Paths.get(argument))){
                throw new LackOfAccessException();
            }
            if (!argument.trim().equals("")) {
                executor.executeScript(argument.trim());
            } else {
                executeScriptCommand.add("please, enter this command with file name");
            }
            executor.getScript().clear();
        } catch (NoSuchCommandException e) {
            executeScriptCommand.add("no such command to execute");
        } catch (LackOfAccessException e) {
            executeScriptCommand.add("the file does not have execute permission");
        }
        return executeScriptCommand;
    }

    public static ArrayList<String> getExecuteScriptCommand() {
        return executeScriptCommand;
    }
}
