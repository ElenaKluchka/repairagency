package com.elenakliuchka.repairagency.db.service;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

import exception.DBException;
import exception.NotUniqueException;

/**
 * Abstract class with basic function on db tables.
 * 
 * @author Kliuchka Olena
 *
 * @param <T>
 */
public abstract class AbstractEntityService<T> {        
   
    public abstract void save(T object) throws SQLException, NotUniqueException, DBException;

    public abstract T find(int id);
    
    public abstract T find(T object) throws DBException;
    
    protected final String tableName;
    
    protected Connection connection;
    
    protected AbstractEntityService(Connection connection, String tableName){
        this.connection = connection;
        this.tableName=tableName;
    }
}
