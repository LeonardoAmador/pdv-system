package dao;

import model.Sale;
import model.SaleItem;
import model.Customer;
import model.Product;
import connection.ConnectionFactory;
	
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class SaleDAO {

    public int createSale(Sale sale) {
        String saleSql = "INSERT INTO sale (customer_id, total_amount) VALUES (?, ?)";
        String saleItemSql = "INSERT INTO saleitem (sale_id, product_id, quantity, price_per_item) VALUES (?, ?, ?, ?)";

        Connection conn = null;
        PreparedStatement saleStmt = null;
        PreparedStatement saleItemStmt = null;
        ResultSet generatedKeys = null;

        try {
            conn = ConnectionFactory.getConnection();
            conn.setAutoCommit(false);

            // Insert into sale table
            saleStmt = conn.prepareStatement(saleSql, Statement.RETURN_GENERATED_KEYS);
            saleStmt.setInt(1, sale.getCustomer().getId());
            saleStmt.setDouble(2, sale.getTotalAmount());
            saleStmt.executeUpdate();

            // Get generated sale ID
            generatedKeys = saleStmt.getGeneratedKeys();
            int saleId = 0;
            if (generatedKeys.next()) {
                saleId = generatedKeys.getInt(1);
                sale.setId(saleId);
            } else {
                throw new SQLException("Creating sale failed, no ID obtained.");
            }

            // Insert into sale_item table
            saleItemStmt = conn.prepareStatement(saleItemSql);
            for (SaleItem item : sale.getSaleItems()) {
                item.setSale(sale); // Set the sale reference
                saleItemStmt.setInt(1, saleId);
                saleItemStmt.setInt(2, item.getProduct().getId());
                saleItemStmt.setInt(3, item.getQuantity());
                saleItemStmt.setDouble(4, item.getPricePerItem());
                saleItemStmt.addBatch();
            }
            saleItemStmt.executeBatch();

            conn.commit();
            return saleId;
        } catch (SQLException e) {
            e.printStackTrace();
            if (conn != null) try { conn.rollback(); } catch (SQLException ex) { ex.printStackTrace(); }
            return -1;
        } finally {
            try { if (generatedKeys != null) generatedKeys.close(); } catch (SQLException e) { e.printStackTrace(); }
            try { if (saleStmt != null) saleStmt.close(); } catch (SQLException e) { e.printStackTrace(); }
            try { if (saleItemStmt != null) saleItemStmt.close(); } catch (SQLException e) { e.printStackTrace(); }
            try { if (conn != null) conn.setAutoCommit(true); conn.close(); } catch (SQLException e) { e.printStackTrace(); }
        }
    }

    public List<Sale> getAllSales() {
        List<Sale> sales = new ArrayList<>();
        String saleSql = "SELECT s.id, s.sale_date, s.total_amount, c.id AS customer_id, c.first_name, c.last_name " +
                         "FROM sale s INNER JOIN customer c ON s.customer_id = c.id";
        String saleItemSql = "SELECT si.id, si.sale_id, si.quantity, si.price_per_item, si.total_price, p.id AS product_id, p.name AS product_name " +
                             "FROM saleitem si INNER JOIN product p ON si.product_id = p.id WHERE si.sale_id = ?";

        try (Connection conn = ConnectionFactory.getConnection();
             PreparedStatement saleStmt = conn.prepareStatement(saleSql);
             ResultSet saleRs = saleStmt.executeQuery()) {

            while (saleRs.next()) {
                Sale sale = new Sale();
                sale.setId(saleRs.getInt("id"));
                sale.setSaleDate(saleRs.getTimestamp("sale_date"));
                sale.setTotalAmount(saleRs.getDouble("total_amount"));

                Customer customer = new Customer();
                customer.setId(saleRs.getInt("customer_id"));
                customer.setFirstName(saleRs.getString("first_name"));
                customer.setLastName(saleRs.getString("last_name"));
                sale.setCustomer(customer);

                // Get sale items
                List<SaleItem> saleItems = new ArrayList<>();
                try (PreparedStatement itemStmt = conn.prepareStatement(saleItemSql)) {
                    itemStmt.setInt(1, sale.getId());
                    try (ResultSet itemRs = itemStmt.executeQuery()) {
                        while (itemRs.next()) {
                            SaleItem item = new SaleItem();
                            item.setId(itemRs.getInt("id"));
                            item.setSale(sale);
                            item.setQuantity(itemRs.getInt("quantity"));
                            item.setPricePerItem(itemRs.getDouble("price_per_item"));
                            item.setTotalPrice(itemRs.getDouble("total_price"));

                            Product product = new Product();
                            product.setId(itemRs.getInt("product_id"));
                            product.setName(itemRs.getString("product_name"));
                            item.setProduct(product);

                            saleItems.add(item);
                        }
                    }
                }
                sale.setSaleItems(saleItems);
                sales.add(sale);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return sales;
    }
    
    public Sale getSaleById(int id) {
        String saleSql = "SELECT s.id, s.sale_date, s.total_amount, c.id AS customer_id, c.first_name, c.last_name " +
                         "FROM sale s INNER JOIN customer c ON s.customer_id = c.id WHERE s.id = ?";
        String saleItemSql = "SELECT si.id, si.quantity, si.price_per_item, p.id AS product_id, p.name AS product_name " +
                             "FROM saleitem si INNER JOIN product p ON si.product_id = p.id WHERE si.sale_id = ?";

        Sale sale = null;
        try (Connection conn = ConnectionFactory.getConnection();
             PreparedStatement saleStmt = conn.prepareStatement(saleSql)) {

            saleStmt.setInt(1, id);
            try (ResultSet saleRs = saleStmt.executeQuery()) {
                if (saleRs.next()) {
                    sale = new Sale();
                    sale.setId(saleRs.getInt("id"));
                    sale.setSaleDate(saleRs.getTimestamp("sale_date"));
                    sale.setTotalAmount(saleRs.getDouble("total_amount"));

                    Customer customer = new Customer();
                    customer.setId(saleRs.getInt("customer_id"));
                    customer.setFirstName(saleRs.getString("first_name"));
                    customer.setLastName(saleRs.getString("last_name"));
                    sale.setCustomer(customer);

                    // Get sale items
                    List<SaleItem> saleItems = new ArrayList<>();
                    try (PreparedStatement itemStmt = conn.prepareStatement(saleItemSql)) {
                        itemStmt.setInt(1, sale.getId());
                        try (ResultSet itemRs = itemStmt.executeQuery()) {
                            while (itemRs.next()) {
                                SaleItem item = new SaleItem();
                                item.setId(itemRs.getInt("id"));
                                item.setSale(sale);
                                item.setQuantity(itemRs.getInt("quantity"));
                                item.setPricePerItem(itemRs.getDouble("price_per_item"));

                                Product product = new Product();
                                product.setId(itemRs.getInt("product_id"));
                                product.setName(itemRs.getString("product_name"));
                                item.setProduct(product);

                                saleItems.add(item);
                            }
                        }
                    }
                    sale.setSaleItems(saleItems);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return sale;
    }
    
    public boolean deleteSale(int saleId) {
        String deleteSaleItemSql = "DELETE FROM saleitem WHERE sale_id = ?";
        String deleteSaleSql = "DELETE FROM sale WHERE id = ?";

        Connection conn = null;
        PreparedStatement saleItemStmt = null;
        PreparedStatement saleStmt = null;

        try {
            conn = ConnectionFactory.getConnection();
            conn.setAutoCommit(false);

            // Delete sale items
            saleItemStmt = conn.prepareStatement(deleteSaleItemSql);
            saleItemStmt.setInt(1, saleId);
            saleItemStmt.executeUpdate();

            // Delete sale
            saleStmt = conn.prepareStatement(deleteSaleSql);
            saleStmt.setInt(1, saleId);
            int affectedRows = saleStmt.executeUpdate();

            conn.commit();
            return affectedRows > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            if (conn != null) try { conn.rollback(); } catch (SQLException ex) { ex.printStackTrace(); }
            return false;
        } finally {
            try { if (saleItemStmt != null) saleItemStmt.close(); } catch (SQLException e) { e.printStackTrace(); }
            try { if (saleStmt != null) saleStmt.close(); } catch (SQLException e) { e.printStackTrace(); }
            try { if (conn != null) conn.setAutoCommit(true); conn.close(); } catch (SQLException e) { e.printStackTrace(); }
        }
    }
    
    public boolean updateSale(Sale sale) {
        String updateSaleSql = "UPDATE sale SET customer_id = ?, total_amount = ? WHERE id = ?";
        String deleteSaleItemsSql = "DELETE FROM saleitem WHERE sale_id = ?";
        String insertSaleItemSql = "INSERT INTO saleitem (sale_id, product_id, quantity, price_per_item) VALUES (?, ?, ?, ?)";

        Connection conn = null;
        PreparedStatement updateSaleStmt = null;
        PreparedStatement deleteSaleItemsStmt = null;
        PreparedStatement insertSaleItemStmt = null;

        try {
            conn = ConnectionFactory.getConnection();
            conn.setAutoCommit(false);

            // Update sale
            updateSaleStmt = conn.prepareStatement(updateSaleSql);
            updateSaleStmt.setInt(1, sale.getCustomer().getId());
            updateSaleStmt.setDouble(2, sale.getTotalAmount());
            updateSaleStmt.setInt(3, sale.getId());
            updateSaleStmt.executeUpdate();

            // Delete existing sale items
            deleteSaleItemsStmt = conn.prepareStatement(deleteSaleItemsSql);
            deleteSaleItemsStmt.setInt(1, sale.getId());
            deleteSaleItemsStmt.executeUpdate();

            // Insert new sale items
            insertSaleItemStmt = conn.prepareStatement(insertSaleItemSql);
            for (SaleItem item : sale.getSaleItems()) {
                insertSaleItemStmt.setInt(1, sale.getId());
                insertSaleItemStmt.setInt(2, item.getProduct().getId());
                insertSaleItemStmt.setInt(3, item.getQuantity());
                insertSaleItemStmt.setDouble(4, item.getPricePerItem());
                insertSaleItemStmt.addBatch();
            }
            insertSaleItemStmt.executeBatch();

            conn.commit();
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
            if (conn != null) try { conn.rollback(); } catch (SQLException ex) { ex.printStackTrace(); }
            return false;
        } finally {
            try { if (updateSaleStmt != null) updateSaleStmt.close(); } catch (SQLException e) { e.printStackTrace(); }
            try { if (deleteSaleItemsStmt != null) deleteSaleItemsStmt.close(); } catch (SQLException e) { e.printStackTrace(); }
            try { if (insertSaleItemStmt != null) insertSaleItemStmt.close(); } catch (SQLException e) { e.printStackTrace(); }
            try { if (conn != null) conn.setAutoCommit(true); conn.close(); } catch (SQLException e) { e.printStackTrace(); }
        }
    }


}
