package doan.repository;

import doan.models.*;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;

public abstract class GenericRepository<T> implements IGenericRepository<T>
{
    String dbSet;
    Connection db;
    String query;

    public GenericRepository(String dbSet, Connection db)
    {
        this.db = db;
        this.dbSet = dbSet;
    }

    @Override
    public String GetDbSet()
    {
        return dbSet;
    }

    @Override
    public ResultSet GetAll() throws SQLException
    {
        query = "SELECT * FROM " + dbSet;
        Statement st = db.createStatement();
        return st.executeQuery(query);
    }

    @Override
    public ResultSet Get(int id) throws SQLException
    {
        Statement st = db.createStatement();
        ResultSet tableDesc = st.executeQuery("DESCRIBE " + dbSet);
        if(!tableDesc.next())
            return null;
        String columnName = tableDesc.getString(1);

        query = "SELECT * FROM " + dbSet + " WHERE " + columnName + " = " + id;
        return st.executeQuery(query);
    }

    @Override
    public abstract void Create(T item) throws SQLException;

    @Override
    public abstract void Update(T item) throws SQLException;

    @Override
    public void Delete(int id) throws SQLException
    {
        Statement st = db.createStatement();
        ResultSet res = st.executeQuery("DESCRIBE " + dbSet);
        if(!res.next())
            return;
        String columnName = res.getString(1);
        query = "DELETE FROM " + dbSet + " WHERE "+ columnName + " = " + id;
        st.execute(query);
    }

    @Override
    public abstract List<T> ProcessData(ResultSet result) throws SQLException;
}
