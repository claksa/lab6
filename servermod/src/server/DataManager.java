package server;

import java.io.IOException;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.Set;
import java.util.logging.Logger;

public class DataManager {
    Selector selector;
    static LinkedList<DataHolder> queue = new LinkedList<>();
    static SelectionKey key = null;
    private static final Logger log = Logger.getLogger(DataManager.class.getName());

    public void manageData() {

        selector = Server.getSelector();
        while (Server.running) {
            try {
                selector.select(50);
                Set<SelectionKey> selectedKeys = selector.selectedKeys();
                Iterator<SelectionKey> keyIterator = selectedKeys.iterator();
                while (keyIterator.hasNext()) {
                    key = keyIterator.next();
                    keyIterator.remove();
                    if (key.isValid()) {
                        if (key.isReadable()) {
                            ReceiveDataHandler.receive();
                        }
//                        } else if (key.isWritable() && inProgress.contains(key)) {
//                            requestData(key);
//                            inProgress.remove(key);
//                        }
                    }
                }
                while (!queue.isEmpty()) {
                    RequestDataHandler.requestData();
                }
            } catch (IOException  e) {
                e.printStackTrace();
            }
        }
    }

}
