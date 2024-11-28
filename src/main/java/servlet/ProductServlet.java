package servlet;

import dao.CategoryDAO;
import dao.ProductDAO;
import model.Category;
import model.Product;

import javax.servlet.ServletException;
import javax.servlet.http.*;
import java.io.IOException;
import java.util.List;

public class ProductServlet extends HttpServlet {

    private ProductDAO productDAO = new ProductDAO();
    private CategoryDAO categoryDAO = new CategoryDAO();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String action = request.getServletPath();

        switch (action) {
            case "/create-product":
                showProductForm(request, response);
                break;
            case "/edit-product":
                showEditProductForm(request, response);
                break;
            case "/list-products":
                listProducts(request, response);
                break;
            case "/delete-product":
                deleteProduct(request, response);
                break;
            default:
                response.sendRedirect("home");
                break;
        }
    }

    private void showProductForm(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        List<Category> categories = categoryDAO.getAllCategories();
        request.setAttribute("categories", categories);

        // Forward to productForm.jsp
        request.getRequestDispatcher("/views/productForm.jsp").forward(request, response);
    }

    private void listProducts(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        List<Product> products = productDAO.getAllProducts();
        request.setAttribute("products", products);

        // Forward to productList.jsp
        request.getRequestDispatcher("/views/productList.jsp").forward(request, response);
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String action = request.getServletPath();

        if ("/create-product".equals(action)) {
            createProduct(request, response);
        } else if ("/update-product".equals(action)) {
            updateProduct(request, response);
        } else {
            response.sendRedirect("home");
        }
    }
    
    private void deleteProduct(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String idStr = request.getParameter("id");
        if (idStr != null) {
            try {
                int id = Integer.parseInt(idStr);
                boolean success = productDAO.deleteProduct(id);
                if (success) {
                    // Redirect to the product list with a success message
                    response.sendRedirect("list-products?deleteSuccess=true");
                } else {
                    // Handle case where deletion was not successful
                    response.sendRedirect("list-products?deleteSuccess=false");
                }
            } catch (NumberFormatException e) {
                e.printStackTrace();
                // Handle invalid ID format
                response.sendRedirect("list-products?deleteSuccess=false");
            }
        } else {
            // No ID provided
            response.sendRedirect("list-products?deleteSuccess=false");
        }
    }
    
    private void showEditProductForm(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String idStr = request.getParameter("id");
        if (idStr != null) {
            try {
                int id = Integer.parseInt(idStr);
                Product product = productDAO.getProductById(id);
                if (product != null) {
                    List<Category> categories = categoryDAO.getAllCategories();
                    request.setAttribute("categories", categories);
                    request.setAttribute("product", product);
                    request.getRequestDispatcher("/views/productForm.jsp").forward(request, response);
                } else {
                    response.sendRedirect("list-products?error=ProductNotFound");
                }
            } catch (NumberFormatException e) {
                e.printStackTrace();
                response.sendRedirect("list-products?error=InvalidProductId");
            }
        } else {
            response.sendRedirect("list-products?error=NoProductId");
        }
    }

    private void createProduct(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        // Existing code for creating a product
        String name = request.getParameter("name");
        String priceStr = request.getParameter("price");
        String description = request.getParameter("description");
        String categoryIdStr = request.getParameter("categoryId");

        // Input validation (simplified)
        if (name == null || priceStr == null || description == null || categoryIdStr == null) {
            response.sendRedirect("create-product");
            return;
        }

        double price = Double.parseDouble(priceStr);
        int categoryId = Integer.parseInt(categoryIdStr);

        // Create Product object
        Category category = categoryDAO.getCategoryById(categoryId);
        Product product = new Product();
        product.setName(name);
        product.setPrice(price);
        product.setDescription(description);
        product.setCategory(category);

        // Save product
        productDAO.createProduct(product);

        // Redirect to the product list with a success message
        response.sendRedirect("list-products?success=true");
    }

    private void updateProduct(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String idStr = request.getParameter("id");
        String name = request.getParameter("name");
        String priceStr = request.getParameter("price");
        String description = request.getParameter("description");
        String categoryIdStr = request.getParameter("categoryId");

        if (idStr == null || name == null || priceStr == null || description == null || categoryIdStr == null) {
            response.sendRedirect("list-products?updateSuccess=false");
            return;
        }

        try {
            int id = Integer.parseInt(idStr);
            double price = Double.parseDouble(priceStr);
            int categoryId = Integer.parseInt(categoryIdStr);

            Product existingProduct = productDAO.getProductById(id);
            if (existingProduct != null) {
                existingProduct.setName(name);
                existingProduct.setPrice(price);
                existingProduct.setDescription(description);
                Category category = categoryDAO.getCategoryById(categoryId);
                existingProduct.setCategory(category);

                boolean success = productDAO.updateProduct(existingProduct);
                if (success) {
                    response.sendRedirect("list-products?updateSuccess=true");
                } else {
                    response.sendRedirect("list-products?updateSuccess=false");
                }
            } else {
                response.sendRedirect("list-products?error=ProductNotFound");
            }
        } catch (NumberFormatException e) {
            e.printStackTrace();
            response.sendRedirect("list-products?updateSuccess=false");
        }
    }


}
