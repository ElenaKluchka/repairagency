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

import com.elenakliuchka.repairagency.db.service.EmployeeService;
import com.elenakliuchka.repairagency.entity.Employee;
import com.elenakliuchka.repairagency.entity.Role;
import com.elenakliuchka.repairagency.util.Constants;

import exception.DBException;

public class EmployeeServiceTest {
    private static final String JDBC_DRIVER = "org.h2.Driver";
    private static final String DB_URL = "jdbc:h2:~/repairagency";
    private static final String URL_CONNECTION = "jdbc:h2:~/repairagency;user=sa;password=;MODE=MySQL";
    private static final String USER = "sa";
    private static final String PASS = "";

    Connection connection;

    @BeforeClass
    public static void createEmployeeTable()
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
            String sql = "DROP TABLE IF EXISTS employee";
            statement.executeUpdate(sql);
            sql = "CREATE TABLE IF NOT EXISTS employee ( \r\n"
                    + " `id` INT UNSIGNED NOT NULL AUTO_INCREMENT,\r\n" + 
                    "  `name` VARCHAR(45) NULL,\r\n" + 
                    "  `email` VARCHAR(20) NULL DEFAULT NULL,\r\n" + 
                    "  `password` VARCHAR(15) NULL DEFAULT NULL,\r\n" + 
                    "  `role` ENUM('MANAGER', 'MASTER') NULL DEFAULT NULL,\r\n" + 
                    "  `phone` VARCHAR(25) NOT NULL,\r\n" + 
                    "  `salary` DECIMAL(10,2) NULL);";
                  
            
            statement.executeUpdate(sql);
            sql = "ALTER TABLE employee ADD CONSTRAINT email_UNIQUE UNIQUE(email);";
            statement.executeUpdate(sql);
            sql = "ALTER TABLE employee ADD CONSTRAINT id_UNIQUE UNIQUE(id);";
            statement.executeUpdate(sql);
            sql = "ALTER TABLE employee ADD CONSTRAINT phone_UNIQUE UNIQUE(phone);";
            statement.executeUpdate(sql);
        }
    }
    
    @Before
    public void insertData() throws SQLException {
        try (Connection connection = DriverManager.getConnection(DB_URL, USER,
                PASS); Statement statement = connection.createStatement()) {
            String sql = "INSERT INTO employee (name, email, password,phone,role) VALUES ('user', 'cust@gmail.com', 'pass','111','MANAGER');";
            statement.executeUpdate(sql);
            sql = "INSERT INTO employee (name, email, password,phone,role) VALUES ('user2', 'cust2@gmail.com', 'pass2','123456','MASTER');";
            statement.executeUpdate(sql);
        }
    }
    
    @After
    public void removeData() throws SQLException {
        try (Connection connection = DriverManager.getConnection(DB_URL, USER,
                PASS); Statement statement = connection.createStatement()) {
            String sql = "DELETE FROM employee where name='user'";
            statement.executeUpdate(sql);
            sql = "DELETE FROM employee where name='user2'";
            statement.executeUpdate(sql);
        }
    }
    
    @AfterClass
    public static void afterTest() throws SQLException, ClassNotFoundException {
        Class.forName(JDBC_DRIVER);
        try (Connection con = DriverManager.getConnection(URL_CONNECTION);
                Statement statement = con.createStatement()) {
            String sql = "DROP TABLE IF EXISTS employee";
            statement.executeUpdate(sql);
        }
    }
    
    @Test
    public void testfindEmployee() throws SQLException, DBException {
        try (Connection connection = DriverManager.getConnection(DB_URL, USER,
                PASS)) {
            EmployeeService employeeService = new EmployeeService(connection);
            Employee employee= employeeService
                    .find(new Employee("user", "pass"));
            Employee expectedEmployee = new Employee("user", "pass");
            expectedEmployee.setEmail("cust@gmail.com");
            expectedEmployee.setPhone("111");
            expectedEmployee.setSalary(0.0);
            expectedEmployee.setRole(Role.MANAGER);
            assertEquals(expectedEmployee, employee);
        }
    }
    
    @Test
    public void testfindEmployeeByRole() throws SQLException, DBException {
        try (Connection connection = DriverManager.getConnection(DB_URL, USER,
                PASS)) {
            EmployeeService employeeService = new EmployeeService(connection);
            List<Employee> employee= employeeService.findEmployeesByRole(Role.MANAGER);                    
         
            assertEquals(1, employee.size());
        }
    }
}
