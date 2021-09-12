package server.lib;

import client.Validator;
import client.ConsoleManager;
import server.commands.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Commander {
    protected List<Commandable> commandsList;
    Validator validator;


    public Commander(){
        commandsList = new ArrayList<>();
    }

    public List<Commandable> getCommandsList(FileManager fileManager,Scanner scanner){
        CollectionManager collectionManager = new CollectionManager(fileManager);
        ConsoleManager consoleManager = new ConsoleManager(scanner);
        validator = new Validator(consoleManager);
        commandsList.add(new Add(collectionManager,validator));
        commandsList.add(new AddMin(collectionManager,validator));
        commandsList.add(new Clear(collectionManager));
        commandsList.add(new FilterStartsWithName(collectionManager));
        commandsList.add(new FilterContainsName(collectionManager));
        commandsList.add(new GroupCountByType(collectionManager));
        commandsList.add(new Help(commandsList));
        commandsList.add(new Info(collectionManager));
        commandsList.add(new Remove(collectionManager,validator));
        commandsList.add(new RemoveLower(collectionManager,validator));
        commandsList.add(new Show(collectionManager));
        commandsList.add(new Shuffle(collectionManager));
        commandsList.add(new Update(collectionManager,validator));
        commandsList.add(new Connect());
        commandsList.add(new ExecuteScript());
        return commandsList;
    }

    public Validator getValidator() {
        return validator;
    }

}
