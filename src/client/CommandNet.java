package client;

import java.io.Serializable;

public class CommandNet implements Serializable {
    private boolean isNotConnectCommand;
    String[] enteredCommand;

    public CommandNet(String[] enteredCommand){
        this.enteredCommand = enteredCommand;
        isNotConnectCommand = true;
    }

    public String[] getEnteredCommand() {
        return enteredCommand;
    }

    public void setConnectCommand(boolean isNotConnectCommand){
        this.isNotConnectCommand = isNotConnectCommand;
    }

    public boolean isNotConnectCommand(){
        return isNotConnectCommand;
    }

}
