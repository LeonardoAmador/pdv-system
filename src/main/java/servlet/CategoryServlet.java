package servlet;

import dao.CategoryDAO;
import model.Category;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

public class CategoryServlet extends HttpServlet {

    private CategoryDAO categoryDAO = new CategoryDAO();

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String method = request.getParameter("_method");

        if ("PUT".equals(method)) {
            doPut(request, response);  // Simulate PUT request
        } else if ("DELETE".equals(method)) {
            doDelete(request, response);  // Simulate DELETE request
        } else {
            // Existing code for category creation
            String name = request.getParameter("name");
            String description = request.getParameter("description");

            if (name == null || name.isEmpty() || description == null || description.isEmpty()) {
                request.setAttribute("error", "All fields are required!");
                request.getRequestDispatcher("/views/createCategory.jsp").forward(request, response);
                return;
            }

            Category category = new Category();
            category.setName(name);
            category.setDescription(description);

            boolean success = categoryDAO.createCategory(category);

            if (success) {
                request.setAttribute("success", "Category created successfully!");
            } else {
                request.setAttribute("error", "Failed to create category.");
            }

            request.getRequestDispatcher("/views/createCategory.jsp").forward(request, response);
        }
    }


    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String id = request.getParameter("id");

        if (id != null && !id.isEmpty()) {
            try {
                // Parse the ID and fetch the category for editing
                int categoryId = Integer.parseInt(id);
                Category category = categoryDAO.getCategoryById(categoryId);

                if (category != null) {
                    request.setAttribute("category", category);
                    request.getRequestDispatcher("/views/editCategory.jsp").forward(request, response);
                } else {
                    // Handle case when category is not found
                    request.setAttribute("error", "Category not found.");
                    request.getRequestDispatcher("/views/listCategories.jsp").forward(request, response);
                }
            } catch (NumberFormatException e) {
                // Handle invalid ID format
                request.setAttribute("error", "Invalid category ID.");
                request.getRequestDispatcher("/views/listCategories.jsp").forward(request, response);
            }
        } else {
            // No ID means we show the list of categories
            List<Category> categories = categoryDAO.getAllCategories();
            request.setAttribute("categories", categories);
            request.getRequestDispatcher("/views/listCategories.jsp").forward(request, response);
        }
    }


    
    @Override
    protected void doPut(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        int id = Integer.parseInt(request.getParameter("id"));
        String name = request.getParameter("name");
        String description = request.getParameter("description");

        Category category = new Category(id, name, description);
        boolean success = categoryDAO.updateCategory(category);

        if (success) {
            response.sendRedirect("list-categories");
        } else {
            request.setAttribute("error", "Failed to update category.");
            request.getRequestDispatcher("/views/editCategory.jsp").forward(request, response);
        }
    }

    @Override
    protected void doDelete(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        int id = Integer.parseInt(request.getParameter("id"));
        boolean success = categoryDAO.deleteCategory(id);

        if (success) {
            // Redirect to the list after successful deletion
            request.setAttribute("success", "Category deleted successfully.");
            List<Category> categories = categoryDAO.getAllCategories();
            request.setAttribute("categories", categories);
            request.getRequestDispatcher("/views/listCategories.jsp").forward(request, response);
        } else {
            request.setAttribute("error", "Failed to delete category.");
            request.getRequestDispatcher("/views/listCategories.jsp").forward(request, response);
        }
    }

}
