package doan.repository;

import doan.models.*;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class UserRepository extends GenericRepository<User> implements IUserRepository
{
    public UserRepository(String dbSet, Connection db)
    {
        super(dbSet, db);
    }

    @Override
    public ResultSet GetAll() throws SQLException
    {
        query = "SELECT userId, login, password, email, roles.roleId, roles.name FROM " + dbSet + " INNER JOIN roles on roles.roleId = users.roleId";
        Statement st = db.createStatement();
        return st.executeQuery(query);
    }
    @Override
    public ResultSet Get(int id) throws SQLException
    {
        query = "SELECT userId, login, password, email, roles.roleId, roles.name FROM " + dbSet + " INNER JOIN roles on roles.roleId = users.roleId" +
                " WHERE userId = " + id;
        Statement st = db.createStatement();
        return st.executeQuery(query);
    }

    @Override
    public void Create(User item) throws SQLException
    {
        query = "INSERT INTO " + dbSet + "(login, password, email, roleId) VALUES (?, ?, ?, ?)";
        PreparedStatement ps = db.prepareStatement(query);
        ps.setString(1, item.getLogin());
        ps.setString(2, item.getPassword());
        ps.setString(3, item.getEmail());
        ps.setInt(4, item.getRole().getId());
        ps.executeUpdate();
    }

    @Override
    public void Update(User item) throws SQLException
    {
        query = "UPDATE " + dbSet + " SET login = ?, password = ?, email = ?, roleId = ? WHERE UserId = " + item.getId();
        PreparedStatement ps = db.prepareStatement(query);
        ps.setString(1, item.getLogin());
        ps.setString(2, item.getPassword());
        ps.setString(3, item.getEmail());
        ps.setInt(4 , item.getRole().getId());
        ps.executeUpdate();
    }

    @Override
    public List<User> ProcessData(ResultSet result) throws SQLException
    {
        List<User> data = new ArrayList<>();
        while(result.next())
        {
            data.add(new User(result.getInt(1), result.getString(2), result.getString(3), result.getString(4),
                    new Role(result.getInt(5), result.getString(6))));
        }
        return data;
    }

    @Override
    public ResultSet FindUserByLoginAndPassword(User user) throws SQLException
    {
        query = "SELECT * FROM " + dbSet +
                " INNER JOIN Roles ON roles.roleId = users.RoleId" +
                " WHERE login = \"" + user.getLogin() + "\" and password = \"" + user.getPassword() + "\"";
        Statement st = db.createStatement();
        return st.executeQuery(query);
    }
}
