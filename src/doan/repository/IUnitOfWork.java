package doan.repository;
 
import java.sql.SQLException;

public interface IUnitOfWork
{
    IRoleRepository getRoles() throws SQLException;
    IUserRepository getUsers();
    ICustomerRepository getCustomers();
    ICategoryRepository getCategories();
    IVendorRepository getVendors();
    IProductRepository getProducts();
    IOrderRepository getOrders();
}
