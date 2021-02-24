package com.elenakliuchka.repairagency.entity;

import java.io.Serializable;

/**
 * Class corresponds db table 'order_master'.
 * 
 * @author Kliuchka Olena
 *
 */

public class OrderMaster implements Serializable{

    private static final long serialVersionUID = -6205048494627536380L;

    int masterId;
    
    int orderId;
    
    public OrderMaster() {        
    }
    
    public OrderMaster(int masterId, int orderId) {
        super();
        this.masterId = masterId;
        this.orderId = orderId;
    }

    public int getMasterId() {
        return masterId;
    }

    public void setMasterId(int masterId) {
        this.masterId = masterId;
    }

    public int getOrderId() {
        return orderId;
    }

    public void setOrderId(int orderId) {
        this.orderId = orderId;
    }

    @Override
    public String toString() {
        return "OrderMaster [masterId=" + masterId + ", orderId=" + orderId
                + "]";
    }
}
