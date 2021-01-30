package com.elenakluchka.repair.agency.db.entity;

public class Order {

    private int id;
    
    private String name;
    
    private User clientUser;
    
    private String description;
    
    private Double price;
    
    public Order() {        
    }

    public final int getId() {
        return id;
    }

    public final void setId(int id) {
        this.id = id;
    }

    public final String getName() {
        return name;
    }

    public final void setName(String name) {
        this.name = name;
    }

    public final User getClientUser() {
        return clientUser;
    }

    public final void setClientUser(User user) {
        this.clientUser = user;
    }

    public final String getDescription() {
        return description;
    }

    public final void setDescription(String description) {
        this.description = description;
    }

    public final Double getPrice() {
        return price;
    }

    public final void setPrice(Double price) {
        this.price = price;
    }

}
