package doan.models;

import com.fasterxml.jackson.annotation.JsonIgnore;

import java.util.ArrayList;

public class Cart
{
    public ArrayList<ProductAmount> getProducts() {
        return products;
    }

    public void setProducts(ArrayList<ProductAmount> products) {
        this.products = products;
    }

    private ArrayList<ProductAmount> products;

    public Cart()
    {
        products = new ArrayList<>();
    }

    @JsonIgnore
    public double getTotalPrice()
    {
        double sum = 0;
        for (ProductAmount p: products)
        {
            sum += p.getPrice();
        }
        return sum;
    }

    public boolean isEmpty()
    {
        return products.isEmpty();
    }
}
