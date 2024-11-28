package model;

public class SaleItem {

    private int id;
    private Sale sale;
    private Product product;
    private int quantity;
    private double pricePerItem;
    private double totalPrice;

    public SaleItem() {}

    public SaleItem(int id, Sale sale, Product product, int quantity, double pricePerItem) {
        this.id = id;
        this.sale = sale;
        this.product = product;
        this.quantity = quantity;
        this.pricePerItem = pricePerItem;
        this.totalPrice = quantity * pricePerItem;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Sale getSale() {
        return sale;
    }

    public void setSale(Sale sale) {
        this.sale = sale;
    }

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
        this.totalPrice = this.quantity * this.pricePerItem;
    }

    public double getPricePerItem() {
        return pricePerItem;
    }

    public void setPricePerItem(double pricePerItem) {
        this.pricePerItem = pricePerItem;
        this.totalPrice = this.quantity * this.pricePerItem;
    }

    public double getTotalPrice() {
        return totalPrice;
    }
    
    public void setTotalPrice(double totalPrice) {
        this.totalPrice = totalPrice;
    }

    @Override
    public String toString() {
        return "SaleItem{" +
                "id=" + id +
                ", sale=" + sale +
                ", product=" + product +
                ", quantity=" + quantity +
                ", pricePerItem=" + pricePerItem +
                ", totalPrice=" + totalPrice +
                '}';
    }
}
