package com.elenakliuchka.repairagency.db.entity;

import java.io.Serializable;

public class User implements Serializable{
  
    private static final long serialVersionUID = -2209869156502461274L;
    
    private int id;    
    private String name;    
    private String email;
    private String password;    
    private Role role;
    
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
    
    public final Role getRole() {
        return role;
    }

    public final void setRole(Role role) {
        this.role = role;
    }

    @Override
    public String toString() {
        return "User [id=" + id + ", name=" + name + ", email=" + email
                + ", password=" + password+", role= "+ role + "]";
    }

    public static User createUser(String name, String email) {
        User account= new User();        
        account.setName(name);
        account.setEmail(email);
        return account;
    }    
}
