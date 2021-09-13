package client;

import server.lib.CollectionManager;
import server.models.Ticket;

public class Validator {
    Ticket ticket;
    Integer id;
    ConsoleManager consoleManager;
    CollectionManager collectionManager;

    public Validator(ConsoleManager consoleManager, CollectionManager collectionManager) {
        this.consoleManager = consoleManager;
        this.collectionManager = collectionManager;
    }

    public void setTicket() {
        this.ticket = consoleManager.getTicketObj();
    }

    public void setId() {
        this.id = consoleManager.readInteger("Enter an id: ");
    }

    public Ticket getTicket() {
        return ticket;
    }

    public Integer getId() {
        return id;
    }

    @Override
    public String toString() {
        return "Validator{" +
                "id=" + id +
                '}';
    }
}
