package servlet;

import dao.CustomerDAO;
import model.Customer;

import javax.servlet.ServletException;
import javax.servlet.http.*;
import java.io.IOException;

public class CustomerServlet extends HttpServlet {

    private CustomerDAO customerDAO = new CustomerDAO();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        // Redirect to customer form
        request.getRequestDispatcher("/views/customerForm.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String action = request.getParameter("action");

        if ("search".equals(action)) {
            searchCustomer(request, response);
        } else if ("create".equals(action)) {
            createCustomer(request, response);
        } else if ("update".equals(action)) {
            updateCustomer(request, response);
        } else {
            response.sendRedirect("home");
        }
    }

    private void searchCustomer(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String searchCustomerIdStr = request.getParameter("searchCustomerId");

        if (searchCustomerIdStr != null && !searchCustomerIdStr.trim().isEmpty()) {
            try {
                int customerId = Integer.parseInt(searchCustomerIdStr.trim());
                Customer customer = customerDAO.getCustomerById(customerId);

                if (customer != null) {
                    request.setAttribute("customer", customer);
                } else {
                    request.setAttribute("error", "Customer with ID " + customerId + " not found.");
                }
            } catch (NumberFormatException e) {
                request.setAttribute("error", "Invalid Customer ID format.");
            }
        } else {
            request.setAttribute("error", "Please enter a Customer ID to search.");
        }

        // Forward back to the form with the customer data or error message
        request.getRequestDispatcher("/views/customerForm.jsp").forward(request, response);
    }

    private void createCustomer(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        // Retrieve form data
        String firstName = request.getParameter("firstName").trim();
        String lastName = request.getParameter("lastName").trim();
        String email = request.getParameter("email").trim();
        String phoneNumber = request.getParameter("phoneNumber").trim();
        String address = request.getParameter("address").trim();

        // Create new customer object
        Customer customer = new Customer();
        customer.setFirstName(firstName);
        customer.setLastName(lastName);
        customer.setEmail(email);
        customer.setPhoneNumber(phoneNumber);
        customer.setAddress(address);

        // Save customer to the database
        boolean success = customerDAO.createCustomer(customer);

        if (success) {
            request.setAttribute("customer", customer);
            request.setAttribute("successMessage", "Customer created successfully!");
        } else {
            request.setAttribute("error", "Failed to create customer.");
        }

        // Forward back to the form with the customer data and success/error message
        request.getRequestDispatcher("/views/customerForm.jsp").forward(request, response);
    }

    private void updateCustomer(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String customerIdStr = request.getParameter("customerId");

        if (customerIdStr != null && !customerIdStr.trim().isEmpty()) {
            try {
                int customerId = Integer.parseInt(customerIdStr.trim());

                // Retrieve form data
                String firstName = request.getParameter("firstName").trim();
                String lastName = request.getParameter("lastName").trim();
                String email = request.getParameter("email").trim();
                String phoneNumber = request.getParameter("phoneNumber").trim();
                String address = request.getParameter("address").trim();

                // Create customer object
                Customer customer = new Customer();
                customer.setId(customerId);
                customer.setFirstName(firstName);
                customer.setLastName(lastName);
                customer.setEmail(email);
                customer.setPhoneNumber(phoneNumber);
                customer.setAddress(address);

                // Update customer in the database
                boolean success = customerDAO.updateCustomer(customer);

                if (success) {
                    request.setAttribute("customer", customer);
                    request.setAttribute("successMessage", "Customer updated successfully!");
                } else {
                    request.setAttribute("error", "Failed to update customer.");
                }

            } catch (NumberFormatException e) {
                request.setAttribute("error", "Invalid Customer ID format.");
            }
        } else {
            request.setAttribute("error", "Customer ID is missing.");
        }

        // Forward back to the form with the customer data and success/error message
        request.getRequestDispatcher("/views/customerForm.jsp").forward(request, response);
    }
}
