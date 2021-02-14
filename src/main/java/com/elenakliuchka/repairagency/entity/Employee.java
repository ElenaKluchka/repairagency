package com.elenakliuchka.repairagency.entity;

public class Employee extends User{

    private static final long serialVersionUID = -1540587379836408091L;

    private String address;
    
    private Double salary;

    public final String getAddress() {
        return address;
    }

    public final void setAddress(String address) {
        this.address = address;
    }

    public final Double getSalary() {
        return salary;
    }

    public final void setSalary(Double salary) {
        this.salary = salary;
    }    
}
