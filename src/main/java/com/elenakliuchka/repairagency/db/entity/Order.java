package com.elenakliuchka.repairagency.db.entity;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Date;

public class Order implements Serializable {

    private static final long serialVersionUID = -3721472966658601675L;

    private int id;

    private String name;

    private int client_id;

    private String description;

    private Double price;
    
    private LocalDateTime date;
    
    private OrderManagmentState managementState;

    private OrderWorkState workState;  
    
    private String feedback;

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

    public final int getClient_id() {
        return client_id;
    }

    public final void setClient_id(int client_id) {
        this.client_id = client_id;
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

    public final OrderManagmentState getManagementState() {
        return managementState;
    }

    public final void setManagementState(OrderManagmentState managementState) {
        this.managementState = managementState;
    }

    public final OrderWorkState getWorkState() {
        return workState;
    }

    public final void setWorkState(OrderWorkState workState) {
        this.workState = workState;
    }
    
    public final void setDate(LocalDateTime date) {
        this.date = date;
    }

    public final LocalDateTime getDate() {
        return date;
    }    
    
    public final String getFeedback() {
        return feedback;
    }

    public final void setFeedback(String feedback) {
        this.feedback = feedback;
    }

    @Override
    public String toString() {
        return "Order [id=" + id + ", name=" + name + ", client=" + client_id
                + ", description=" + description + ", price=" + price
                + ", managementState=" + managementState + ", workState="
                + workState +", feedback "+feedback+"]";
    }
    
}
