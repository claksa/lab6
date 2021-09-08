package client;

import server.models.Ticket;

public class Validator {
    Ticket ticket;
    Integer id;
    ConsoleManager consoleManager;

    public Validator(ConsoleManager consoleManager) {
        this.consoleManager = consoleManager;
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
