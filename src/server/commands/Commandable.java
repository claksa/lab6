package server.commands;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * interface with methods that implement command classes
 */
public interface Commandable  {
    String getDescription();
    ArrayList<String> execute (String argument);

    default String getName() {
        return getClass().getSimpleName().toLowerCase();
    }

}
