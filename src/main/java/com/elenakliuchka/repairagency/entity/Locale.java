package com.elenakliuchka.repairagency.entity;

import java.io.Serializable;

public class Locale  implements Serializable{
 
    private static final long serialVersionUID = 5794826919544903914L;

    private int id;
    
    private String name;

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
}
