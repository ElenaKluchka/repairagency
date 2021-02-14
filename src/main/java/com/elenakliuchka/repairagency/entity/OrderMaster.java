package com.elenakliuchka.repairagency.entity;

import java.io.Serializable;

public class OrderMaster implements Serializable{

    private static final long serialVersionUID = -6205048494627536380L;

    int master_id;
    
    int order_id;

    public final int getMaster_id() {
        return master_id;
    }

    public final void setMaster_id(int master_id) {
        this.master_id = master_id;
    }

    public final int getOrder_id() {
        return order_id;
    }

    public final void setOrder_id(int order_id) {
        this.order_id = order_id;
    }    
}
