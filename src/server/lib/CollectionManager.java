package server.lib;

import server.models.Ticket;
import server.models.TicketType;

import java.time.ZonedDateTime;
import java.util.*;
import java.util.stream.Collectors;

import static client.ConsoleManager.PrintMsg;

public class CollectionManager {
    private Vector<Ticket> tickets;
    private final FileManager fileManager;

    /**
     * Constructor
     * check fields in collection
     *
     * @param fileManager to read collection from file
     */

    public CollectionManager(FileManager fileManager) {
        this.fileManager = fileManager;
        this.tickets = fileManager.readData();
        fileManager.checkData(tickets);
    }

    /**
     * @return information about collection
     */

    public String getInformation() {
        String dataSimpleName = tickets.getClass().getSimpleName();
        ZonedDateTime creationDate = ZonedDateTime.now();
        String size = String.valueOf(tickets.size());
        String res = "";
        res += "\n" + "collection type: " + dataSimpleName + "\n";
        res += "collection creation time: " + creationDate + "\n";
        res += "number of items in the collection: " + size + "\n";
        return res;
    }

    public String getStringElements() {
        return String.valueOf(tickets);
    }

    /**
     * getter for collection that have read from file (i hope)
     *
     * @return collection
     */

    public Vector<Ticket> getTickets() {
        return tickets;
    }

    /**
     * save collection to file
     */

    public void save() {
        fileManager.saveData(tickets);
    }

    /**
     * alternative getter for id
     *
     * @return generated id
     */

    public Integer getID() {
        int maxID = 0;
        for (Ticket t : tickets) {
            int id = t.getId();
            if (maxID < id) {
                maxID = id;
            }
        }
        return maxID + 1;
    }

    /**
     * change ticket object by id
     *
     * @param update ticket object
     */

    public void update(Ticket update) {
        Vector<Ticket> res = new Vector<>();
        for (Ticket t : tickets) {
            if (!t.getId().equals(update.getId())) {
                res.add(t);
            }
        }
        res.add(update);
        tickets = res;
    }

    /**
     * add item to collection
     * @param ticket object
     */


    public  void addItem(Ticket ticket) {
        System.out.println("ticket object needs to add: " + ticket);
        for (Ticket t : tickets) {
            tickets.add(ticket);
            PrintMsg("added\n");
            return;
        }
    }

    /**
     * add new item to collection with min id
     * @param ticket object
     */


    public void addMin(Ticket ticket) {
        for (Ticket t : tickets) {
            if (ticket.getId() > t.getId()) {
                PrintMsg("you cannot add\n");
                return;
            }
        }
        tickets.add(ticket);
    }

    /**
     * check id
     *
     * @param ID of ticket/venue object
     * @return boolean value: true if id's are equal to each other
     */


    public boolean isEqualId(Integer ID) {
        for (Ticket t : tickets) {
            if (t.getId().equals(ID)) {
                return true;
            }
        }
        return false;
    }

    /**
     * remove item by id
     *
     * @param id id to compare
     */

    public void removeById(Integer id) {
        tickets.stream()
                .filter(t -> t.getId().equals(id))
                .peek(t -> tickets.remove(t));
    }

    public void remove(Integer id) {
        for (Ticket t : tickets) {
            if (t.getId().equals(id)) {
                tickets.remove(t);
                return;
            }
        }
    }


    public boolean removeIfLowerId(Ticket ticket) {
        return tickets.removeIf(ticket1 -> (ticket.getId() > ticket1.getId()));
    }

    /**
     * find substring in ticket's name (in start)
     * @param substring to find in field name
     * @return object with this substring in name
     */

    public String startsWithSubstring(String substring) {
        StringBuilder list = new StringBuilder();
        for (Ticket t : tickets) {
            if (t.getName().startsWith(substring.trim())) {
                list.append(t).append("\n");
            }
        }
        return list.toString();
    }

    /**
     * find substring in ticket's name
     *
     * @param substring to find in field name
     * @return object with this substring in name
     */

    public String containsSomeSubstring(String substring) {
        StringBuilder list = new StringBuilder();
        for (Ticket t : tickets) {
            if (t.getName().contains(substring.trim())) {
                list.append(list).append(t).append("\n");
            }
        }
        return list.toString();
    }

    public void clear() {
        tickets.clear();
    }


    public ArrayList<String> groupCount() {
        ArrayList<String> out = new ArrayList<>();
        Map<TicketType,Long> ticketsByType = tickets.stream().collect(Collectors.groupingBy(Ticket::getType, Collectors.counting()));
        for (Map.Entry<TicketType,Long> item: ticketsByType.entrySet()){
            out.add(item.getKey() + "-" + item.getValue());
        }
        return out;
    }
}
