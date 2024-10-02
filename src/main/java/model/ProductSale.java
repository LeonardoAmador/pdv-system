package model;

public class ProductSale {

    private int id;
    private Product product;
    private Sale sale;

    public ProductSale() {}

    public ProductSale(int id, Product product, Sale sale) {
        this.id = id;
        this.product = product;
        this.sale = sale;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

    public Sale getSale() {
        return sale;
    }

    public void setSale(Sale sale) {
        this.sale = sale;
    }

    @Override
    public String toString() {
        return "ProductSale{" +
                "id=" + id +
                ", product=" + product +
                ", sale=" + sale +
                '}';
    }
}
