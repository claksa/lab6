package server.models;

import java.time.LocalDateTime;
import java.util.Objects;

public class Ticket {
    private final int id;
    private final String name;
    private final Coordinates coordinates;
    private final LocalDateTime creationDate;
    private final int price;
    private final TicketType type; //Поле может быть null
    private final Venue venue; //Поле может быть null
    private static int lastId=0;

    public Ticket(String name, LocalDateTime creationDate, Coordinates coordinates, int price, TicketType type, Venue venue) {
        this.name = name;
        this.coordinates = coordinates;
        this.creationDate = creationDate;
        this.price = price;
        this.type = type;
        this.venue = venue;
        lastId++;
        id=lastId;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Ticket ticket = (Ticket) o;
        return id == ticket.id;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    public LocalDateTime getCreationDate() {
        return creationDate;
    }

    public Integer getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public TicketType getType() {
        return type;
    }

    public Coordinates getCoordinates() {
        return coordinates;
    }

    public int getPrice() {
        return price;
    }

    public Venue getVenue() {
        return venue;
    }

    @Override
    public String toString() {
        return "Ticket{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", coordinates=" + coordinates +
                ", creationDate=" + creationDate +
                ", price=" + price +
                ", type=" + type +
                ", venue=" + venue +
                '}';
    }
}
