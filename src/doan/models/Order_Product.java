package doan.models;

public class Order_Product
{
    private int orderId;
    private  Order order;

    private int productId;
    private Product product;
    private int amount;

    public Order_Product()
    {

    }

    public Order_Product(int orderId, Order order, int productId, Product product, int amount) {
        this.orderId = orderId;
        this.order = order;
        this.productId = productId;
        this.product = product;
        this.amount = amount;
    }

    public int getOrderId()
    {
        return orderId;
    }

    public void setOrderId(int orderId)
    {
        this.orderId = orderId;
    }

    public Order getOrder()
    {
        return order;
    }

    public void setOrder(Order order)
    {
        this.order = order;
    }

    public int getProductId()
    {
        return productId;
    }

    public void setProductId(int productId)
    {
        this.productId = productId;
    }

    public Product getProduct()
    {
        return product;
    }

    public void setProduct(Product product)
    {
        this.product = product;
    }

    public int getAmount() {
        return amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }
}
