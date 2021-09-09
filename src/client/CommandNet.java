package client;

import java.io.Serializable;

public class CommandNet implements Serializable {
    String[] enteredCommand;

    public CommandNet(String[] enteredCommand){
        this.enteredCommand = enteredCommand;
    }


    public String[] getEnteredCommand() {
        return enteredCommand;
    }
}
