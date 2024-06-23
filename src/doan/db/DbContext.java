package doan.db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
 

import doan.models.*;

public class DbContext {
    private Connection connection;

    public DbContext(String connectionString, String user, String password)
            throws ClassNotFoundException, SQLException {
        Class.forName("com.mysql.cj.jdbc.Driver");
        connection = DriverManager.getConnection(connectionString, user, password);
    }

    public Connection getConnection() {
        return connection;
    }

    public void Initialize() throws SQLException {
        UnitOfWork uof = new UnitOfWork(connection);
        uof.getUsers().Create(new User(0, "tai10", "11", "abc@gmail.com", new Role(2, "user")));
        uof.getUsers().Create(new User(0, "yan", "yan", "abc@gmail.com", new Role(2, "user")));
 
    }
}
