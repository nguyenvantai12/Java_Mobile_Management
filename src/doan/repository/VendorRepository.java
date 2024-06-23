package doan.repository;

import doan.models.*;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class VendorRepository extends GenericRepository<Vendor> implements IVendorRepository
{
    public VendorRepository(String dbSet, Connection db)
    {
        super(dbSet, db);
    }

    @Override
    public ResultSet GetAll() throws SQLException
    {
        query = "SELECT   VendorId,  VendorName,  Email,  PhoneNumber,  country" +
                " FROM " + dbSet ;
        Statement st = db.createStatement();
        return st.executeQuery(query);
    }
    @Override
    public void Create(Vendor item) throws SQLException
    {
        query = "INSERT INTO " + dbSet + " (VendorName, Email, PhoneNumber, Country) VALUES (?, ?, ?, ?)";
        System.out.println(item.toString());
        PreparedStatement ps = db.prepareStatement(query);
        ps.setString(1, item.getName());
        ps.setString(2, item.getEmail());
        ps.setString(3, item.getPhoneNumber());
        ps.setString(4, item.getCountry());
        ps.executeUpdate();
    }

    @Override
    public void Update(Vendor item) throws SQLException
    {
        query = "UPDATE " + dbSet + " SET VendorName = ?, Email = ?, PhoneNumber = ?, Country = ? WHERE VendorId =" + item.getId();
        PreparedStatement ps = db.prepareStatement(query);
        System.out.println(item.toString());
        ps.setString(1, item.getName());
        ps.setString(2, item.getEmail());
        ps.setString(3, item.getPhoneNumber());
        ps.setString(4, item.getCountry());
        ps.executeUpdate();
    }

    @Override
    public List<Vendor> ProcessData(ResultSet result) throws SQLException
    {
        List<Vendor> data = new ArrayList<>();
        while (result.next())
        {
            data.add(new Vendor(result.getInt(1), result.getString(2), result.getString(3), result.getString(4), result.getString(5)));
        }
        return data;
    }
    @Override
	public void Delete(int id) throws SQLException {
	 
		query = "DELETE FROM  vendors WHERE VendorId = " + id;
		 
		db.createStatement().executeUpdate(query);

	}
}
