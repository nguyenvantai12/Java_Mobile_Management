package doan.db;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.Dictionary;
import java.util.Hashtable;
import doan.repository.*;
import com.fasterxml.jackson.core.JsonProcessingException;

 import doan.models.*;
 import doan.services.*;
 
public class Handler
{
    private Dictionary<Class, IService> serviceDictionary;

    public Handler(Connection db)
    {
        IUnitOfWork unitOfWork = new UnitOfWork(db);
        UserService userService = new UserService(unitOfWork.getUsers(), unitOfWork.getCustomers());

        serviceDictionary = new Hashtable<>();
        serviceDictionary.put(User.class, userService);
        serviceDictionary.put(Customer.class, userService);
        serviceDictionary.put(Vendor.class, new VenderService(unitOfWork.getVendors()));
 
        serviceDictionary.put(Category.class, new CategoryService(unitOfWork.getCategories()));
        serviceDictionary.put(Product.class, new ProductService(unitOfWork.getProducts()));
        serviceDictionary.put(Order.class, new OrderService(unitOfWork.getOrders()));
    }

    public Message<?> Handle(Message<?> message) throws SQLException, JsonProcessingException
    {
        IService service = serviceDictionary.get(message.getType());
        if(service == null)
            return new Message<>("ERROR", String.class, "Lỗi không thấy service");

        return service.Execute(message.getHead(), message.getObject(), message.getType());
    }
}
