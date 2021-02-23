package com.elenakliuchka.repairagency.entity;

import java.io.Serializable;

public class Employee  implements Serializable{

    private static final long serialVersionUID = -1540587379836408091L;
    
    private int id;
    
    private String name; 

    private String email;
    
    private String password;
    
    private Role role;
 
    private String phone;
    
    private Double salary;
    
    public Employee() {        
    }

    public Employee(String name, String password) {
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

    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public Double getSalary() {
        return salary;
    }

    public void setSalary(Double salary) {
        this.salary = salary;
    }   
}
