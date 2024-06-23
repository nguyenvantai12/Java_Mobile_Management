package doan.repository;
import doan.models.*;

import java.sql.ResultSet;
import java.sql.SQLException;

public interface ICustomerRepository extends IGenericRepository<Customer>
{
    ResultSet GetCustomerByUserId(int id) throws SQLException;

    void Register(Customer customer) throws SQLException;
}
