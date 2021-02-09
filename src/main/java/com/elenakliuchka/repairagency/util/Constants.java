package com.elenakliuchka.repairagency.util;

/**
 * Constants.
 * @author Kliuchka Olena.
 *
 */
public class Constants {
    
    private Constants() {        
    }
    public static final String PROPERTY_FILE = "app.properties";
    public static final String CONNECTION_URL_PROP = "connection.url";
    public static final String CONNECTION_URL="jdbc:mysql://127.0.0.1:3306/repair_agency?autoReconnect=true&useSSL=false&serverTimezone=UTC&user=root&password=pass";
    public static final String CONNECTION_ERROR = "Failed to make connection!";
}
