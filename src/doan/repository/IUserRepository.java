package doan.repository;
import doan.models.*;

import java.sql.ResultSet;
import java.sql.SQLException;

public interface IUserRepository extends IGenericRepository<User>
{
    ResultSet FindUserByLoginAndPassword(User user) throws SQLException;
}
