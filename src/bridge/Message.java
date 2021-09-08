package bridge;

import java.io.Serializable;

public class Message implements Serializable {
    String message;

    public Message(String message){
        this.message = message;
//        PrintMessage(message);
    }

    public String getMessage() {
        return message;
    }

    public void setErrorMessage(String message) {
        this.message = "Error: " + message;
        PrintMessage();
    }

    public void setMessage(String message) {
        this.message = message;
        PrintMessage();
    }

    public void PrintMessage(){
        System.out.println(message);
    }

    @Override
    public String toString() {
        return "Message [" + message + '\'' +
                ']';
    }
}
