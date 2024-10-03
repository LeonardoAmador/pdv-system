package servlet;

import dao.CategoryDAO;
import model.Category;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class CategoryServlet extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        
        System.out.println("CategoryServlet is called");
        
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

        CategoryDAO categoryDAO = new CategoryDAO();
        boolean success = categoryDAO.createCategory(category);
        
        if (!success) {
            request.setAttribute("error", "Failed to create category.");
            request.getRequestDispatcher("/views/createCategory.jsp").forward(request, response);
            return;
        }

        response.sendRedirect(request.getContextPath() + "/category-list");
    }
}
