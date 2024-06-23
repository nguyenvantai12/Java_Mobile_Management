package doan.repository;

import doan.models.*;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class OrderRepository extends GenericRepository<Order> implements IOrderRepository
{
    public OrderRepository(String dbSet, Connection db)
    {
        super(dbSet, db);
    }

    @Override
    public ResultSet GetAll() throws SQLException
    {
        query = "SELECT orderid, orderstatus, totalprice, customers.customerId, customerName, phoneNumber, " +
                "users.userId, login, users.password, email, roles.roleId, roles.Name, orders.date" +
                " FROM " + dbSet +
                " INNER JOIN Customers ON customers.customerId = orders.customerId" +
                " INNER JOIN Users ON users.userId = customers.userId" +
                " INNER JOIN Roles ON roles.roleId = users.roleId";
        Statement st = db.createStatement();
        return st.executeQuery(query);
    }

    @Override
    public ResultSet Get(int id) throws SQLException
    {
        query = "SELECT orderid, orderstatus, totalprice, customers.customerId, customerName, phoneNumber, " +
                "users.userId, login, users.password, email, roles.roleId, roles.Name, orders.date" +
                " FROM " + dbSet +
                " INNER JOIN Customers ON customers.customerId = orders.customerId" +
                " INNER JOIN Users ON users.userId = customers.userId" +
                " INNER JOIN Roles ON roles.roleId = users.roleId" +
                " WHERE orderId = " + id;
        Statement st = db.createStatement();
        return st.executeQuery(query);
    }

    @Override
    public void Create(Order item) throws SQLException
    {
        query = "INSERT INTO " + dbSet + "(TotalPrice, CustomerId) VALUES (?,?)";
        PreparedStatement ps = db.prepareStatement(query);
        ps.setDouble(1, item.getTotalPrice());
        ps.setInt(2, item.getCustomer().getId());
        ps.executeUpdate();

        query = "SELECT orderId FROM " + dbSet + " ORDER BY OrderId DESC LIMIT 1";
        Statement st = db.createStatement();
        ResultSet res = st.executeQuery(query);
        res.next();
        int id = res.getInt(1);

        query = "INSERT INTO order_product VALUES (?,?,?)";
        ps = db.prepareStatement(query);
        for (Order_Product op : item.getProducts())
        {
            ps.setInt(1, id);
            ps.setInt(2, op.getProductId());
            ps.setInt(3 ,op.getAmount());
            ps.executeUpdate();
        }
    }

    @Override
    public void Update(Order item) throws SQLException
    {
    	System.out.println(item.toString());
        query = "UPDATE " + dbSet + " SET OrderStatus = ?, TotalPrice = ?, CustomerId = ? WHERE OrderId = " + item.getId();
        PreparedStatement ps = db.prepareStatement(query);
        ps.setString(1, item.getStatus());
        ps.setDouble(2, item.getTotalPrice());
        ps.setInt(3, item.getCustomer().getId());
        ps.executeUpdate();
    }

    @Override
    public List<Order> ProcessData(ResultSet result) throws SQLException
    {
        List<Order> data = new ArrayList<>();
        while(result.next())
        {
            Order order = new Order(result.getInt(1), result.getString(2), result.getDouble(3),
                    new Customer(result.getInt(4), result.getString(5), result.getString(6),
                            new User(result.getInt(7), result.getString(8), result.getString(9), result.getString(10),
                                    new Role(result.getInt(11), result.getString(12)))),
                    new ArrayList<>(), result.getDate(13));
            order.setProducts(GetOrderProduct(order.getId()));
            data.add(order);
        }
        return data;
    }
    private ArrayList<Order_Product> GetOrderProduct(int id) throws SQLException
    {
        query = "SELECT order_product.productId, amount, products.productName, products.ProductDescription, products.price, " +
                "vendors.vendorId, vendors.vendorName, vendors.email, vendors.phonenumber, vendors.country" +
                " FROM shop.order_product " +
                " INNER JOIN products ON products.ProductId = order_product.ProductId " +
                " INNER JOIN vendors ON order_product.productId = vendors.vendorId" +
                " WHERE orderId = " + id;
        ArrayList<Order_Product> result = new ArrayList<>();

        Statement st  = db.createStatement();
        ResultSet res = st.executeQuery(query);
        while (res.next())
        {
            result.add(new Order_Product(id, null, res.getInt(1),
                    new Product(res.getInt(1), res.getString(3), res.getString(4), res.getDouble(5),
                                new Vendor(res.getInt(6), res.getString(7), res.getString(8), res.getString(9),
                                            res.getString(10)), null),
                                res.getInt(2)));
        }
        return result;
    }

    @Override
    public ResultSet GetOrdersByCustomerId(int customerId) throws SQLException {
        query = "SELECT orderid, orderstatus, totalprice, customers.customerId, customerName, phoneNumber, " +
                "users.userId, login, users.password, email, roles.roleId, roles.Name, orders.date" +
                " FROM " + dbSet +
                " INNER JOIN Customers ON customers.customerId = orders.customerId" +
                " INNER JOIN Users ON users.userId = customers.userId" +
                " INNER JOIN Roles ON roles.roleId = users.roleId" +
                " WHERE customers.customerId = " + customerId;
        Statement st = db.createStatement();
        return st.executeQuery(query);
    }
}
