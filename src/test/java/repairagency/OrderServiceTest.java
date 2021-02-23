package repairagency;

import static org.junit.Assert.assertEquals;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;
import java.util.Properties;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import com.elenakliuchka.repairagency.db.service.OrderService;
import com.elenakliuchka.repairagency.entity.Order;
import com.elenakliuchka.repairagency.util.Constants;

import exception.DBException;

public class OrderServiceTest {
    private static final String JDBC_DRIVER = "org.h2.Driver";
    private static final String DB_URL = "jdbc:h2:~/repair_agency";
    private static final String URL_CONNECTION = "jdbc:h2:~/repair_agency;user=sa;password=;MODE=MySQL";
    private static final String USER = "sa";
    private static final String PASS = "";

    Connection connection;   

    @BeforeClass
    public static void createOrderTable()
            throws SQLException, ClassNotFoundException {
        Class.forName(JDBC_DRIVER);
        
        CustomerServiceTest.createCustomerTable();

        try (OutputStream output = new FileOutputStream(
                Constants.PROPERTY_FILE)) {
            Properties prop = new Properties();
            prop.setProperty("connection.url", URL_CONNECTION);
            prop.store(output, null);
        } catch (IOException io) {
            io.printStackTrace();
        }
        
        try (Connection connection = DriverManager.getConnection(DB_URL, USER,
                PASS); Statement statement = connection.createStatement()) {
            String sql = "DROP TABLE IF EXISTS customer";
            statement.executeUpdate(sql);
            sql = "CREATE TABLE IF NOT EXISTS customer ( \r\n"
                    + "                    id INT UNSIGNED NOT NULL AUTO_INCREMENT,\r\n"
                    + "                    name VARCHAR(45) NULL DEFAULT NULL,\r\n"
                    + "                    email VARCHAR(20) NULL DEFAULT NULL,\r\n"
                    + "                    password VARCHAR(15) NULL DEFAULT NULL,\r\n"
                    + "                    phone VARCHAR(25) NOT NULL,\r\n"
                    + "                    balance DECIMAL(10,2) NULL DEFAULT 0,\r\n"
                    + "                    PRIMARY KEY (`id`));";
            
            statement.executeUpdate(sql);
            sql = "ALTER TABLE customer ADD CONSTRAINT email_UNIQUE UNIQUE(email);";
            statement.executeUpdate(sql);
            sql = "ALTER TABLE customer ADD CONSTRAINT id_UNIQUE UNIQUE(id);";
            statement.executeUpdate(sql);
            sql = "ALTER TABLE customer ADD CONSTRAINT phone_UNIQUE UNIQUE(phone);";
            statement.executeUpdate(sql);
      
            sql = "DROP TABLE IF EXISTS orders";
            statement.executeUpdate(sql);
            sql = "CREATE TABLE IF NOT EXISTS orders ( \r\n  "
                    + "id INT UNSIGNED NOT NULL AUTO_INCREMENT,\r\n" + 
                    "  customer_id INT UNSIGNED NOT NULL,\r\n" + 
                    "  name VARCHAR(45) NOT NULL,\r\n" + 
                    "  date DATETIME NULL DEFAULT CURRENT_TIMESTAMP,\r\n" + 
                    "  price DOUBLE NULL DEFAULT NULL,\r\n" + 
                    "  description VARCHAR(500) NULL DEFAULT NULL,\r\n" + 
                    "  managment_state ENUM('NEW', 'WAIT_FOR_PAYMENT', 'PAYED', 'CANCELED') NOT NULL DEFAULT 'NEW',\r\n" + 
                    "  work_state ENUM('NEW', 'IN_WORK', 'FINISHED') NOT NULL DEFAULT 'NEW',\r\n" + 
                    "  feedback VARCHAR(500) NULL);";           
            statement.executeUpdate(sql);            
            
            sql = "ALTER TABLE orders ADD CONSTRAINT id_orders_UNIQUE UNIQUE(id);";
            statement.executeUpdate(sql);
      /*      sql = "ALTER TABLE order_ ADD CONSTRAINT fk_customer_orders\r\n" + 
                    "    FOREIGN KEY (customer_id)\r\n" + 
                    "    REFERENCES customer (id)\r\n" + 
                    "    ON DELETE CASCADE\r\n" + 
                    "    ON UPDATE CASCADE)"; 
            statement.executeUpdate(sql);*/
            
            sql = "INSERT INTO customer (name, email, password,phone) VALUES ('user', 'cust@gmail.com', 'pass','111');";
            statement.executeUpdate(sql);
            sql = "INSERT INTO customer (name, email, password,phone) VALUES ('user2', 'cust2@gmail.com', 'pass2','123456');";
            statement.executeUpdate(sql);
        }
    }
    
    @Before
    public void insertData() throws SQLException {
               try (Connection connection = DriverManager.getConnection(DB_URL, USER,
                PASS); Statement statement = connection.createStatement()) {
          String sql = "INSERT INTO orders (customer_id, description, name) VALUES ('2', 'asdsdfdf', 'refrigerator');";
            statement.executeUpdate(sql);        
        }
    }
    
    @After
    public void removeData() throws SQLException {
        try (Connection connection = DriverManager.getConnection(DB_URL, USER,
                PASS); Statement statement = connection.createStatement()) {
            String sql = "DELETE FROM orders where id=1";
            statement.executeUpdate(sql);
        }
    }
    
    @Test
    public void testfindAllOrders() throws SQLException, DBException {
        try (Connection connection = DriverManager.getConnection(DB_URL, USER,
                PASS)) {
            OrderService orderService = new OrderService(connection);      
            List<Order> orderList =orderService.findAll(0, 3);     
            assertEquals(1, orderList.size());
        }
    }
    
    @AfterClass
    public static void afterTest() throws SQLException, ClassNotFoundException {
        Class.forName(JDBC_DRIVER);
        CustomerServiceTest.afterTest();
        try (Connection con = DriverManager.getConnection(URL_CONNECTION);
                Statement statement = con.createStatement()) {
            String sql = "DROP TABLE IF EXISTS orders";
            statement.executeUpdate(sql);
        }
    }
}
