package models;

import mainlib.CollectionManager;

import java.io.Serializable;
import java.time.LocalDateTime;

public class Ticket implements Serializable {
    private int id;
    private final String name;
    private final Coordinates coordinates;
    private final LocalDateTime creationDate;
    private final int price;
    private final TicketType type;
    private final Venue venue;
    private static Integer lastId = 0;

    public Ticket(String name,Coordinates coordinates, int price, TicketType type, Venue venue) {
        this.name = name;
        this.coordinates = coordinates;
        this.creationDate = LocalDateTime.now();
        this.price = price;
        this.type = type;
        this.venue = venue;
        this.id = getIncLastId();
        generateId();
    }


    private void generateId(){
        for (Integer id: CollectionManager.getIds()) {
            if (id.equals(this.id)){
                this.id = getIncLastId(); // криво
            }
        }
    }

    private static Integer getIncLastId(){
        return ++lastId;
    }


    public LocalDateTime getCreationDate() {
        return creationDate;
    }

    public Integer getId() {
        return id;
    }

    public static Integer getLastId() {
        return lastId;
    }

    public static void setLastId(Integer lastId) {
        Ticket.lastId = lastId;
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
        return "\nTicket: " + "\n" + '[' +
                "id = " + id + "\n" +
                "name = " + name  + "\n" +
                "coordinates = " + coordinates + "\n" +
                "price = " + price + "\n" +
                "type = " + type + "\n" +
                "venue = " + venue + ']';
    }
}
