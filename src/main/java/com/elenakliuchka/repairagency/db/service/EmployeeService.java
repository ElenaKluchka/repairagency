package com.elenakliuchka.repairagency.db.service;

import java.sql.Connection;
import java.util.List;
import java.util.logging.Logger;

import com.elenakliuchka.repairagency.entity.Employee;

public class EmployeeService extends AbstractEntityService<Employee> {
    
    private final static String TABLE_NAME = "employee_info";
    
    private static final Logger LOGGER = Logger
            .getLogger(ClientService.class.getName());

    public EmployeeService(Connection connection) {
        super(connection, TABLE_NAME);
    }
 
    @Override
    public void save(Employee object) {
    }

    @Override
    public void remove(int id) {
    }

    @Override
    public Employee find(int id) {
        return null;
    }

    @Override
    public Employee find(Employee object) {
        return null;
    }

    @Override
    public Long getCount() {
        return null;
    }
    
    
    @Override
    public List<Employee> findAll(int start, int max) {
        // TODO Auto-generated method stub
        return null;
    }
}
