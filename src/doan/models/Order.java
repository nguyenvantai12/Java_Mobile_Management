package doan.models;

import com.fasterxml.jackson.annotation.JsonIgnore;

import java.sql.Date;
import java.util.ArrayList;

public class Order
{
    private int id;
    private Customer customer;
    private String status;
    private double totalPrice;

    private Date datetime;
    private ArrayList<Order_Product> products;

    public Order()
    {

    }

    public Order(int id, String status, double totalPrice, Customer customer, ArrayList<Order_Product> products, Date date)
    {
        this.id = id;
        this.customer = customer;
        this.status = status;
        this.totalPrice = totalPrice;
        this.products = products;
        this.datetime = date;
    }

    public int getId()
    {
        return id;
    }
    public void setId(int id)
    {
        this.id = id;
    }

    public Customer getCustomer()
    {
        return customer;
    }
    public void setCustomer(Customer customer)
    {
        this.customer = customer;
    }

    public String getStatus()
    {
        return status;
    }
    public void setStatus(String status)
    {
        this.status = status;
    }

    public double getTotalPrice()
    {
        return totalPrice;
    }
    public void setTotalPrice(double totalPrice)
    {
        this.totalPrice = totalPrice;
    }

    public Date getDatetime() {
        return datetime;
    }
    public void setDatetime(Date datetime) {
        this.datetime = datetime;
    }

    public ArrayList<Order_Product> getProducts()
    {
        return products;
    }
    public void setProducts(ArrayList<Order_Product> products)
    {
        this.products = products;
    }

    @JsonIgnore
    public String getCustomerName(){return customer.getName();}
    @JsonIgnore
    public String getCustomerEmail(){return customer.getEmail();}

	@Override
	public String toString() {
		return "Order [id=" + id + ", customer=" + customer + ", status=" + status + ", totalPrice=" + totalPrice
				+ ", datetime=" + datetime  ;
	}

    
}
