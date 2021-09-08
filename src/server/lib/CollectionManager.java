package server.lib;

import server.models.Ticket;
import server.models.TicketType;

import java.time.ZonedDateTime;
import java.util.List;
import java.util.Objects;
import java.util.Vector;
import java.util.stream.Collectors;

import static client.ConsoleManager.PrintMsg;

public class CollectionManager {
    private Vector<Ticket> tickets;
    private final FileManager fileManager;

    /**
     * Constructor
     * check fields in collection
     * @param fileManager to read collection from file
     */

    public CollectionManager(FileManager fileManager) {
        this.fileManager = fileManager;
        this.tickets = fileManager.readData();
        fileManager.checkData(tickets);
    }

    /**
     *
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

    public String getStringElements(){
       tickets.forEach(e-> System.out.println(e.toString()));
       return String.valueOf(tickets);
    }

    /**
     * getter for collection that have read from file (i hope)
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


    public void addItem(Ticket ticket) {
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
     * @param id id to compare
     */

    public void removeById(Integer id) {
        tickets.stream()
                .filter(t -> t.getId().equals(id))
                .peek(t -> tickets.remove(t))
                .forEach(System.out::println);
    }

    /**
     * remove item with lower id
     * @param ticket object
     */

    public void removeLower(Ticket ticket) {
        for (Ticket t : tickets) {
            if (ticket.getId() > t.getId()) {
                PrintMsg("cannot be removed\n");
                return;
            }
        }
        tickets.remove(ticket);
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

    /**
     * group collection by enum type
     */

//      too lazy to think about how to make it more abstract
    public void assort() {

        Vector<Ticket> res1 = new Vector<>();
        Vector<Ticket> res2 = new Vector<>();
        Vector<Ticket> res3 = new Vector<>();
        for (Ticket t : tickets) {
            if (t.getType().equals(TicketType.USUAL)) {
                res1.add(t);
            }
            if (t.getType().equals(TicketType.CHEAP)) {
                res2.add(t);
            }
            if (t.getType().equals(TicketType.BUDGETARY)) {
                res3.add(t);
            }
        }
        System.out.println(res1);
        PrintMsg("Number of elements " +"(type - "+ TicketType.USUAL + "): " + res1.size());

        System.out.println(res2);
        PrintMsg("Number of elements " +"(type - "+ TicketType.CHEAP + "): " + res2.size());

        System.out.println(res3);
        PrintMsg("Number of elements " +"(type - "+ TicketType.BUDGETARY + "): " + res3.size());

    }
}
