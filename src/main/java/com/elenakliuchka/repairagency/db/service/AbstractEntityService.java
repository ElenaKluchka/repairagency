package com.elenakliuchka.repairagency.db.service;

import java.sql.Connection;
import java.util.List;

public abstract class AbstractEntityService<T> {    
    
    public abstract List<T> findAll(int start, int max);
 //   public abstract List<T> findAll();

    public abstract void save(T object);    

    public abstract void remove(int id);
    
//    public abstract void remove(T object);

    public abstract T find(int id);
    
    public abstract T find(T object);
    
    public abstract Long getCount();
    
    protected final String tableName;
    protected Connection connection;
    
    protected AbstractEntityService(Connection connection, String tableName){
        this.connection = connection;
        this.tableName=tableName;
    }
}
