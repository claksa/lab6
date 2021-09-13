package client;

import server.models.Ticket;

public class TicketInfoHolder {
    private Ticket ticket;
    private Integer id;

    public Ticket getTicket() {
        return ticket;
    }

    public void setTicket(Ticket ticket) {
        this.ticket = ticket;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    @Override
    public String toString() {
        return "TicketInfoHolder{" +
                "ticket=" + ticket +
                ", id=" + id +
                '}';
    }
}
