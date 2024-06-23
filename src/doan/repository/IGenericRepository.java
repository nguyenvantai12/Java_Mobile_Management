package doan.repository;
import doan.models.*;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

public interface IGenericRepository<T>
{
    String GetDbSet();
    ResultSet GetAll() throws SQLException;
    ResultSet Get(int id) throws SQLException;
    void Create(T item) throws SQLException;
    void Update(T item) throws SQLException;
    void Delete(int id) throws SQLException;
    List<T> ProcessData(ResultSet result) throws SQLException;
}
