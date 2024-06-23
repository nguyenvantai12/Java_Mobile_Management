package doan.db;

 

import doan.repository.*;

import java.sql.Connection;

public class UnitOfWork implements IUnitOfWork
{
    private Connection db;

    private IRoleRepository roleRepository = null;
    private IUserRepository userRepository = null;
    private ICustomerRepository customerRepository = null;
    private IVendorRepository vendorRepository = null;
    private ICategoryRepository categoryRepository = null;
    private IProductRepository productRepository = null;
    private IOrderRepository orderRepository = null;

    public UnitOfWork(Connection db)
    {
        this.db = db;
    }

    @Override
    public IRoleRepository getRoles()
    {
        if(roleRepository == null)
            roleRepository = new RoleRepository("Roles", db);
        return roleRepository;
    }

    @Override
    public IUserRepository getUsers()
    {
        if(userRepository == null)
            userRepository = new UserRepository("Users", db);
        return userRepository;
    }

    @Override
    public ICustomerRepository getCustomers()
    {
        if(customerRepository == null)
            customerRepository = new CustomerRepository("Customers", db);
        return customerRepository;
    }

    @Override
    public ICategoryRepository getCategories()
    {
        if(categoryRepository == null)
            categoryRepository = new CategoryRepository("Categories", db);
        return categoryRepository;
    }

    @Override
    public IVendorRepository getVendors()
    {
        if(vendorRepository == null)
            vendorRepository = new VendorRepository("Vendors", db);
        return vendorRepository;
    }

    @Override
    public IProductRepository getProducts()
    {
        if(productRepository == null)
            productRepository = new ProductRepository("Products", db);
        return productRepository;
    }

    @Override
    public IOrderRepository getOrders()
    {
        if(orderRepository == null)
            orderRepository = new OrderRepository("Orders", db);
        return orderRepository;
    }
}
