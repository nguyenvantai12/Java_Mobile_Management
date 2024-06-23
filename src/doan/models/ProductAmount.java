package doan.models;
public class ProductAmount
{
    private Product product;
    private int amount;

    public ProductAmount()
    {

    }

    public ProductAmount(Product product, int amount)
    {
        this.product = product;
        this.amount = amount;
    }

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

    public int getAmount() {
        return amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }

    public double getPrice()
    {
        return product.getPrice() * amount;
    }

    public String getName()
    {
        return product.getName();
    }
}
