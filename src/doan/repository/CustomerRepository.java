package doan.repository;

import doan.models.*;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class CustomerRepository extends GenericRepository<Customer> implements ICustomerRepository
{
    public CustomerRepository(String dbSet, Connection db)
    {
        super(dbSet, db);
    }


    public ResultSet GetCustomerByUserId(int id) throws SQLException
    {
        query = "select customerId, customers.customerName, customers.phoneNumber, " +
                "users.userId, users.login, users.password, users.email, " +
                "roles.roleId, roles.name " +
                "from " + dbSet +
                " inner join users on users.userId = customers.userID" +
                " inner join roles on roles.roleId = users.roleId" +
                " where users.userId = " + id;
        Statement st = db.createStatement();
        return st.executeQuery(query);
    }

    @Override
    public ResultSet GetAll() throws SQLException
    {
        query = "select customerId, customers.customerName, customers.phoneNumber, users.userId, users.login, users.password, users.email, roles.roleId, roles.name " +
                "from " + dbSet +
                " inner join users on users.userId = customers.userID" +
                " inner join roles on roles.roleId = users.roleId";
        Statement st = db.createStatement();
        return st.executeQuery(query);
    }

    @Override
    public void Create(Customer item) throws SQLException
    {
        query = "INSERT INTO " + dbSet + " (UserId, customerName, PhoneNumber) VALUES (?,?,?)";
        PreparedStatement ps = db.prepareStatement(query);
        ps.setInt(1, item.getUser().getId());
        ps.setString(2, item.getName());
        ps.setString(3, item.getPhoneNumber());
        ps.executeUpdate();
    }

    @Override
    public void Update(Customer item) throws SQLException
    {
        query = "UPDATE " + dbSet + " SET userId = ?, customerName = ?, phoneNumber = ? WHERE UserId = " + item.getId();
        PreparedStatement ps = db.prepareStatement(query);
        ps.setInt(1, item.getUser().getId());
        ps.setString(2, item.getName());
        ps.setString(3, item.getPhoneNumber());
        ps.executeUpdate();
    }

    @Override
    public List<Customer> ProcessData(ResultSet result) throws SQLException
    {
        List<Customer> data = new ArrayList<>();
        while(result.next())
        {
            data.add(new Customer(result.getInt(1), result.getString(2), result.getString(3),
                    new User(result.getInt(4), result.getString(5), result.getString(6), result.getString(7),
                            new Role(result.getInt(8), result.getString(9)))));
        }
        return data;
    }

    @Override
    public void Register(Customer customer) throws SQLException {
        query = "INSERT INTO shop.users (login, password, email) VALUES (?, ?, ?)";
        PreparedStatement ps = db.prepareStatement(query);
        ps.setString(1, customer.getUser().getLogin());
        ps.setString(2, customer.getUser().getPassword());
        ps.setString(3, customer.getUser().getEmail());
        ps.executeUpdate();

        query = "SELECT UserId FROM users ORDER BY UserId DESC LIMIT 1";
        Statement st = db.createStatement();
        ResultSet res = st.executeQuery(query);
        res.next();
        int id = res.getInt(1);

        query = "INSERT INTO " + dbSet + " (customerName, phoneNumber, UserId) VALUES (?, ?, ?)";
        ps = db.prepareStatement(query);
        ps.setString(1, customer.getName());
        ps.setString(2, customer.getPhoneNumber());
        ps.setInt(3, id);
        ps.executeUpdate();
    }
}
