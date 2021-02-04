package com.elenakliuchka.repairagency.db.entity;

import java.util.List;

public class Client extends User{
    private static final long serialVersionUID = -8830684043818853764L;
    
    private Double balance;
 //   private int numberOrders;   
    private List<Order> orders;
    
    public final Double getBalance() {
        return balance;
    }
    public final void setBalance(Double balance) {
        this.balance = balance;
    }
        
    public final List<Order> getOrders() {
        return orders;
    }
    public final void setOrders(List<Order> orders) {
        this.orders = orders;
    }
    @Override
    public String toString() {
        return "Client [balance=" + balance + ", orders=" + orders
                + "]";
    }
}
