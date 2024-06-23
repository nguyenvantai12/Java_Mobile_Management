package doan.services;


 
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import doan.models.*;
import doan.repository.*;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

public class UserService implements IService
{
    private IUserRepository userRepository;
    private ICustomerRepository customerRepository;
    private ObjectMapper mapper = new ObjectMapper();

    public UserService(IUserRepository urep, ICustomerRepository crep)
    {
        userRepository = urep;
        customerRepository = crep;
    }

    @Override
    public Message<?> Execute(String command, String objectJson, Class<?> type) throws SQLException, JsonProcessingException
    {
        User object = null;
        Customer customer = null;
        if(objectJson != null)
        {
            if(type == User.class)
                object = mapper.readValue(objectJson, User.class);
            else if (type == Customer.class)
                customer = mapper.readValue(objectJson, Customer.class);
        }

        switch (command)
        {
            case "LOGIN":
                return Login(object);
            case "REGISTER":
                return Register(customer);
            case "GETCUSTOMERS":
                return GetAllCustomers();
            case "CHANGEROLE":
                return ChangeRole(object);
            default:
                return new Message<>("ERROR", String.class, "Không tìm thấy thao tác");
        }
    }

    private Message<?> ChangeRole(User object) throws SQLException
    {
        userRepository.Update(object);
        return new Message<>("SUCCESS", String.class, "Thay đổi quyền thành công");
    }

    private Message<?> GetAllCustomers() throws SQLException, JsonProcessingException
    {
        ResultSet res = customerRepository.GetAll();
        List<Customer> customers = customerRepository.ProcessData(res);
        String customersJson = mapper.writeValueAsString(customers);
        return new Message<>("SUCCESS", List.class, customersJson);
    }

    private Message<?> Login(User u) throws SQLException, JsonProcessingException
    {
        ResultSet res = userRepository.FindUserByLoginAndPassword(u);
        List<User> foundArray = userRepository.ProcessData(res);
        if(foundArray.size() == 0)
            return new Message<>("ERROR", String.class, "username hoặc mật khẩu sai");

        User found = foundArray.get(0);
        ResultSet customerByUserId = customerRepository.GetCustomerByUserId(found.getId());
        List<Customer> foundList = customerRepository.ProcessData(customerByUserId);
        if(foundList.size() <= 0)
            return new Message<>("ERROR", String.class, "Không tìm thấy");
        Customer customer = foundList.get(0);
        String customerJson = mapper.writeValueAsString(customer);
        return new Message<>("SUCCESS", Customer.class, customerJson);
    }
    private Message<?> Register(Customer c)
    {
        try
        {
            customerRepository.Register(c);
        } catch (SQLException e)
        {
            e.printStackTrace();
            return new Message<>("ERROR", String.class, "Lỗi tạo người dùng");
        }
        return new Message<>("SUCCESS", String.class, "Người dùng đã được tạo thành công");
    }
}
