package model;

import java.util.Date;
import java.util.List;

public class Sale {

    private int id;
    private Date saleDate;
    private Customer customer;
    private double totalAmount;
    private List<SaleItem> saleItems;

    public Sale() {}

    public Sale(int id, Date saleDate, Customer customer, double totalAmount, List<SaleItem> saleItems) {
        this.id = id;
        this.saleDate = saleDate;
        this.customer = customer;
        this.totalAmount = totalAmount;
        this.saleItems = saleItems;
    }
 
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Date getSaleDate() {
        return saleDate;
    }

    public void setSaleDate(Date saleDate) {
        this.saleDate = saleDate;
    }

    public Customer getCustomer() {
        return customer;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }

    public double getTotalAmount() {
        return totalAmount;
    }

    public void setTotalAmount(double totalAmount) {
        this.totalAmount = totalAmount;
    }

    public List<SaleItem> getSaleItems() {
        return saleItems;
    }

    public void setSaleItems(List<SaleItem> saleItems) {
        this.saleItems = saleItems;
    }

    @Override
    public String toString() {
        return "Sale{" +
                "id=" + id +
                ", saleDate=" + saleDate +
                ", customer=" + customer +
                ", totalAmount=" + totalAmount +
                ", saleItems=" + saleItems +
                '}';
    }
}
