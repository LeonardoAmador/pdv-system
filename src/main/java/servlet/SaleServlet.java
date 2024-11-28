package servlet;

import dao.SaleDAO;
import dao.CustomerDAO;
import dao.ProductDAO;
import model.Sale;
import model.SaleItem;
import model.Customer;
import model.Product;

import javax.servlet.ServletException;
import javax.servlet.http.*;
import java.io.IOException;
import java.util.*;

public class SaleServlet extends HttpServlet {

	private SaleDAO saleDAO = new SaleDAO();
	private CustomerDAO customerDAO = new CustomerDAO();
	private ProductDAO productDAO = new ProductDAO();

	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String action = request.getServletPath();

		switch (action) {
		case "/create-sale":
			showSaleForm(request, response);
			break;
		case "/list-sales":
			listSales(request, response);
			break;
		case "/edit-sale":
			showSaleForm(request, response);
			break;
		case "/delete-sale":
			deleteSale(request, response);
			break;
		// Cases for edit and delete will be added later
		default:
			response.sendRedirect("home");
			break;
		}
	}

	private void showSaleForm(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String saleIdStr = request.getParameter("id");
		Sale sale = null;
		if (saleIdStr != null) {
			try {
				int saleId = Integer.parseInt(saleIdStr);
				sale = saleDAO.getSaleById(saleId);
			} catch (NumberFormatException e) {
				e.printStackTrace();
			}
		}

		// Fetch customers and products
		List<Customer> customers = customerDAO.getAllCustomers();
		List<Product> products = productDAO.getAllProducts();
		request.setAttribute("customers", customers);
		request.setAttribute("products", products);

		if (sale != null) {
			request.setAttribute("sale", sale);
		}

		// Forward to saleForm.jsp
		request.getRequestDispatcher("/views/saleForm.jsp").forward(request, response);
	}

	private void listSales(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		List<Sale> sales = saleDAO.getAllSales();
		request.setAttribute("sales", sales);

		// Forward to saleList.jsp (to be created)
		request.getRequestDispatcher("/views/saleList.jsp").forward(request, response);
	}

	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// Handle form submission for creating a sale
		String action = request.getServletPath();
		System.out.println("doPost action: " + action); // Debugging statement

		if ("/create-sale".equals(action)) {
			createSale(request, response);
		} else if ("/update-sale".equals(action)) {
			updateSale(request, response);
		} else {
			response.sendRedirect("home");
		}
	}

	private void createSale(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String customerIdStr = request.getParameter("customerId");
		String[] productIds = request.getParameterValues("productId[]");
		String[] quantities = request.getParameterValues("quantity[]");

		// Input validation
		if (customerIdStr == null || productIds == null || quantities == null) {
			response.sendRedirect("create-sale?error=InvalidInput");
			return;
		}

		try {
			int customerId = Integer.parseInt(customerIdStr);
			Customer customer = customerDAO.getCustomerById(customerId);

			List<SaleItem> saleItems = new ArrayList<>();
			double totalAmount = 0.0;

			for (int i = 0; i < productIds.length; i++) {
				int productId = Integer.parseInt(productIds[i]);
				int quantity = Integer.parseInt(quantities[i]);
				Product product = productDAO.getProductById(productId);
				double pricePerItem = product.getPrice();
				double totalPrice = pricePerItem * quantity;

				SaleItem item = new SaleItem();
				item.setProduct(product);
				item.setQuantity(quantity);
				item.setPricePerItem(pricePerItem);

				saleItems.add(item);
				totalAmount += totalPrice;

				System.out.println("Accumulated totalAmount after adding product ID " + productId + ": " + totalAmount);
			}

			System.out.println("Final totalAmount: " + totalAmount);

			if (Double.isNaN(totalAmount) || Double.isInfinite(totalAmount)) {
				System.err.println("Invalid totalAmount detected: " + totalAmount);
				response.sendRedirect("create-sale?error=InvalidTotalAmount");
				return;
			}

			Sale sale = new Sale();
			sale.setCustomer(customer);
			sale.setSaleDate(new Date());
			sale.setTotalAmount(totalAmount);
			sale.setSaleItems(saleItems);

			int saleId = saleDAO.createSale(sale);
			if (saleId > 0) {
				response.sendRedirect("list-sales?success=true");
			} else {
				response.sendRedirect("create-sale?error=SaleCreationFailed");
			}

		} catch (NumberFormatException e) {
			e.printStackTrace();
			response.sendRedirect("create-sale?error=InvalidNumberFormat");
		}
	}

	private void deleteSale(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String idStr = request.getParameter("id");
		if (idStr != null) {
			try {
				int saleId = Integer.parseInt(idStr);
				boolean success = saleDAO.deleteSale(saleId);
				if (success) {
					response.sendRedirect("list-sales?deleteSuccess=true");
				} else {
					response.sendRedirect("list-sales?deleteSuccess=false");
				}
			} catch (NumberFormatException e) {
				e.printStackTrace();
				response.sendRedirect("list-sales?deleteSuccess=false");
			}
		} else {
			response.sendRedirect("list-sales?deleteSuccess=false");
		}
	}

	private void updateSale(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String saleIdStr = request.getParameter("saleId");
		String customerIdStr = request.getParameter("customerId");
		String[] productIds = request.getParameterValues("productId[]");
		String[] quantities = request.getParameterValues("quantity[]");

		System.out.println("saleIdStr: " + saleIdStr);
		System.out.println("customerIdStr: " + customerIdStr);
		System.out.println("productIds: " + Arrays.toString(productIds));
		System.out.println("quantities: " + Arrays.toString(quantities));

		// Input validation
		if (saleIdStr == null || customerIdStr == null || productIds == null || quantities == null) {
			response.sendRedirect("edit-sale?error=InvalidInput&id=" + saleIdStr);
			return;
		}

		try {
			int saleId = Integer.parseInt(saleIdStr);
			int customerId = Integer.parseInt(customerIdStr);
			Customer customer = customerDAO.getCustomerById(customerId);

			List<SaleItem> saleItems = new ArrayList<>();
			double totalAmount = 0.0;

			for (int i = 0; i < productIds.length; i++) {
				int productId = Integer.parseInt(productIds[i]);
				int quantity = Integer.parseInt(quantities[i]);
				Product product = productDAO.getProductById(productId);
				double pricePerItem = product.getPrice();
				double totalPrice = pricePerItem * quantity;

				SaleItem item = new SaleItem();
				item.setProduct(product);
				item.setQuantity(quantity);
				item.setPricePerItem(pricePerItem);

				saleItems.add(item);
				totalAmount += totalPrice;
			}

			Sale sale = new Sale();
			sale.setId(saleId);
			sale.setCustomer(customer);
			sale.setSaleDate(new Date()); // You may choose to keep the original date
			sale.setTotalAmount(totalAmount);
			sale.setSaleItems(saleItems);

			boolean success = saleDAO.updateSale(sale);
			if (success) {
				response.sendRedirect("list-sales?updateSuccess=true");
			} else {
				response.sendRedirect("edit-sale?id=" + saleId + "&error=SaleUpdateFailed");
			}

		} catch (NumberFormatException e) {
			e.printStackTrace();
			response.sendRedirect("edit-sale?error=InvalidNumberFormat&id=" + request.getParameter("saleId"));
		} catch (Exception e) {
			e.printStackTrace();
			response.sendRedirect("edit-sale?id=" + request.getParameter("saleId") + "&error=ExceptionOccurred");
		}
	}
}
