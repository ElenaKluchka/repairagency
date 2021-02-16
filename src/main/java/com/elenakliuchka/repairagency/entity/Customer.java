package com.elenakliuchka.repairagency.entity;

import java.io.Serializable;
import java.util.List;

public class Customer implements Serializable{

    private static final long serialVersionUID = -1540587379836408091L;
    
    private int id;
    
    private String name; 

    private String email;
    
    private String password; 
    
    private int locale_id;
    
    private String phone;
    
    private Double balance;
    
    private List<Order> orders;

    public Customer() {        
    }
    
    public Customer(String name, String password) {
        this.name = name;
        this.password = password;
    }    

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public int getLocale_id() {
        return locale_id;
    }

    public void setLocale_id(int locale_id) {
        this.locale_id = locale_id;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public Double getBalance() {
        return balance;
    }

    public void setBalance(Double balance) {
        this.balance = balance;
    }

    public void setOrders(List<Order> orders) {
        this.orders = orders;
    }

    public List<Order> getOrders() {
        return orders;
    }

    @Override
    public String toString() {
        return "Customer [id=" + id + ", name=" + name + ", email=" + email
                + ", locale_id=" + locale_id + ", phone=" + phone + ", balance="
                + balance + ", orders=" + orders + "]";
    } 
}
