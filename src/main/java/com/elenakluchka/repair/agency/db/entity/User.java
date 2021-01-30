package com.elenakluchka.repair.agency.db.entity;

public class User {
    
    private int id;    
    private String name;    
    private String email;
    private String password;
    
    public User() {        
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
    public final String getEmail() {
        return email;
    }
    public final void setEmail(String email) {
        this.email = email;
    }
    public final String getPassword() {
        return password;
    }
    public final void setPassword(String password) {
        this.password = password;
    }    

    @Override
    public String toString() {
        return "User [id=" + id + ", name=" + name + ", email=" + email
                + ", password=" + password + "]";
    }

    public static User createUser(String name, String email) {
        User account= new User();        
        account.setName(name);
        account.setEmail(email);
        return account;
    }    
}
