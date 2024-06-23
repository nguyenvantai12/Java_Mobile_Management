package doan.models;
public class Category_Product
{
    private int CategoryId;
    private Category category;

    private int ProductId;
    private Product product;

    public Category_Product(int categoryId, int productId)
    {
        CategoryId = categoryId;
        ProductId = productId;
    }

    public int getCategoryId()
    {
        return CategoryId;
    }

    public void setCategoryId(int categoryId)
    {
        CategoryId = categoryId;
    }

    public Category getCategory()
    {
        return category;
    }

    public void setCategory(Category category)
    {
        this.category = category;
    }

    public int getProductId()
    {
        return ProductId;
    }

    public void setProductId(int productId)
    {
        ProductId = productId;
    }

    public Product getProduct()
    {
        return product;
    }

    public void setProduct(Product product)
    {
        this.product = product;
    }

}
