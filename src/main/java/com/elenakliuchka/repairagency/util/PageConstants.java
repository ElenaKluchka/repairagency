package com.elenakliuchka.repairagency.util;

public class PageConstants {
    /*   public static final String PAGE_LOGIN_INDEX = "/index.jsp";
    public static final String PAGE_CLIENT_ORDERS= "/client/orders.jsp";
    public static final String PAGE_MANAGE_ORDERS= "/manager/manage_orders.jsp";
    public static final String PAGE_MASTER = "/master/master.jsp";
    public static final String PAGE_SUCCESS ="success.jsp";
    public static final String PAGE_ERROR ="/error/page-not-found.jsp";*/

    /*   public static final String PAGE_LOGIN_INDEX = "/login.jsp";
    public static final String PAGE_CLIENT_ORDERS_JSP= "client/orders.jsp";
    
    public static final String PAGE_CLIENT_ORDERS= "client/orders";
    public static final String PAGE_CLIENT_ORDERS_RED= "orders.jsp";
    
    public static final String PAGE_MANAGE_ORDERS= "/manager/manage_orders.jsp";
    public static final String PAGE_MASTER = "/master/master.jsp";
    public static final String PAGE_SUCCESS ="success.jsp";
    public static final String PAGE_ERROR ="error/page-not-found";
    public static final String PAGE_PAGE_NOT_FOUND ="/error/page-not-found";*/

    public static final String CONTROLLER_URL = "/do";

    public static final String PAGE_LOGIN = "/login";

    public static final String PAGE_NOT_FOUND = "/error/page-not-found";

    public static final String PAGE_CUSTOMER_ORDERS = "/client/orders";

    public static final String PAGE_MASTER_ORDERS = "/master/orders";

    public static final String PAGE_MANAGER_ORDERS = "/manager/manage_orders";

    public static final String PAGE_MANAGER_CUSTOMERS = "/manager/find_customer";

    public static final String PAGE_MANAGER_EDIT_ORDER = "/manager/edit_order";

    public static final String PAGE_MANAGER_EDIT_ORDER_FORM = "/do/manager/editOrderForm?command=EditOrderForm&orderId=";

    public static final String HOME_PAGE_CUSTOMER = CONTROLLER_URL
            + PAGE_CUSTOMER_ORDERS + "?command=CustomerOrders";

    public static final String HOME_PAGE_MANAGER = CONTROLLER_URL
            + PAGE_MANAGER_ORDERS + "?command=ManagerOrders";

    public static final String HOME_PAGE_MASTER = CONTROLLER_URL
            + PAGE_MASTER_ORDERS + "?command=MasterOrders";
}
