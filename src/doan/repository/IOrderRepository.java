package doan.repository;
import doan.models.*;

import java.sql.ResultSet;
import java.sql.SQLException;

public interface IOrderRepository extends IGenericRepository<Order>
{
    ResultSet GetOrdersByCustomerId(int customerId) throws SQLException;
}
