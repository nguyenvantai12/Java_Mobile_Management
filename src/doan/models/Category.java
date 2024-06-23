package doan.models;
import java.util.ArrayList;

public class Category
{
    private int id;
    private String name;
    private String description;
    private ArrayList<Category_Product> products;

    public Category()
    {

    }
    public Category(int id, String name, String description) {
    	  this.id = id;
          this.name = name;
          this.description = description;
    }

    public Category(int id, String name, String description, ArrayList<Category_Product> products)
    {
        this.id = id;
        this.name = name;
        this.description = description;
        this.products = products;
    }

    public int getId()
    {
        return id;
    }

    public void setId(int id)
    {
        this.id = id;
    }

    public String getName()
    {
        return name;
    }

    public void setName(String name)
    {
        this.name = name;
    }

    public String getDescription()
    {
        return description;
    }

    public void setDescription(String description)
    {
        this.description = description;
    }

    public ArrayList<Category_Product> getProducts()
    {
        return products;
    }

    public void setProducts(ArrayList<Category_Product> products)
    {
        this.products = products;
    }
}
