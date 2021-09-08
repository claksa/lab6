package server.models;



public class Address {
    private final String street; //Поле может быть null
    private final String zipCode;
    private final Location town;

    public Address (String street, String zipCode, Location town){
        this.street = street;
        this.zipCode = zipCode;
        this.town = town;
    }

    public String getStreet() {
        return street;
    }

    public String getZipCode() {
        return zipCode;
    }

    public Location getTown() {
        return town;
    }
}
