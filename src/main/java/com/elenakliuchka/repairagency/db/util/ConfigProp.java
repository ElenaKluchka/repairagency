package com.elenakliuchka.repairagency.db.util;

public class ConfigProp {
    private static java.util.Properties prop = new java.util.Properties();

    private static void loadProperties() {
        // get class loader
        ClassLoader loader = ConfigProp.class.getClassLoader();
        if (loader == null)
            loader = ClassLoader.getSystemClassLoader();

        // assuming you want to load application.properties located in
        // WEB-INF/classes/conf/
        // String propFile = "conf/application.properties";
        String propFile = Constants.PROPERTY_FILE;
        java.net.URL url = loader.getResource(propFile);
        try {
            prop.load(url.openStream());
        } catch (Exception e) {
            System.err
                    .println("Could not load configuration file: " + propFile);
        }
    }

    // ....
    // add your methods here. prop is filled with the content of
    // conf/application.properties

    // load the properties when class is accessed
    static {
        loadProperties();
    }

    public static String getValue(String key) {        
        return prop.getProperty(key);
    }
}
