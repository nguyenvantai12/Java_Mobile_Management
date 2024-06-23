package doan.repository;

import doan.models.*;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class RoleRepository extends GenericRepository<Role> implements IRoleRepository
{

    public RoleRepository(String dbSet, Connection db)
    {
        super(dbSet, db);
    }

    @Override
    public void Create(Role item) throws SQLException
    {
        query = "INSERT INTO " + dbSet + "(name) VALUES (?)";
        PreparedStatement ps = db.prepareStatement(query);
        ps.setString(2, item.getName());
        ps.executeUpdate();
    }

    @Override
    public void Update(Role item) throws SQLException
    {
        query = "UPDATE " + dbSet + " SET name = ? WHERE RoleId = " + item.getId();
        PreparedStatement ps = db.prepareStatement(query);
        ps.setString(2, item.getName());
        ps.executeUpdate();
    }

    @Override
    public List<Role> ProcessData(ResultSet result) throws SQLException
    {
        List<Role> data = new ArrayList<>();
        while(result.next())
        {
            data.add(new Role(result.getInt(1), result.getString(2)));
        }
        return data;
    }
}
