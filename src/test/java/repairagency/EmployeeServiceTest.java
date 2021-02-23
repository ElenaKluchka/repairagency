package repairagency;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Properties;

import org.junit.After;
import org.junit.Before;
import org.junit.BeforeClass;

import com.elenakliuchka.repairagency.util.Constants;

public class EmployeeServiceTest {
    private static final String JDBC_DRIVER = "org.h2.Driver";
    private static final String DB_URL = "jdbc:h2:~/repairagency";
    private static final String URL_CONNECTION = "jdbc:h2:~/repairagency;user=sa;password=;MODE=MySQL";
    private static final String USER = "sa";
    private static final String PASS = "";

    Connection connection;

    @BeforeClass
    public static void beforeTest()
            throws SQLException, ClassNotFoundException {
        Class.forName(JDBC_DRIVER);

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
        }
    }
    
    @Before
    public void insertData() throws SQLException {
        try (Connection connection = DriverManager.getConnection(DB_URL, USER,
                PASS); Statement statement = connection.createStatement()) {
            String sql = "INSERT INTO customer (name, email, password,phone) VALUES ('user', 'cust@gmail.com', 'pass','111');";
            statement.executeUpdate(sql);
            sql = "INSERT INTO customer (name, email, password,phone) VALUES ('user2', 'cust2@gmail.com', 'pass2','123456');";
            statement.executeUpdate(sql);
        }
    }
    
    @After
    public void removeData() throws SQLException {
        try (Connection connection = DriverManager.getConnection(DB_URL, USER,
                PASS); Statement statement = connection.createStatement()) {
            String sql = "DELETE FROM customer where name='user'";
            statement.executeUpdate(sql);
            sql = "DELETE FROM customer where name='user2'";
            statement.executeUpdate(sql);
        }
    }
}
