<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ page import="java.util.List, model.Customer, model.Product, model.SaleItem" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>${sale != null ? 'Edit Sale' : 'Create Sale'}</title>

<!-- Bootstrap CSS -->
<link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/4.0.0/css/bootstrap.min.css">

<!-- Font Awesome for Icons -->
<link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/5.15.3/css/all.min.css">

</head>
<body>

<div class="container mt-5">
    <h2 class="mb-4">${sale != null ? 'Edit Sale' : 'Create New Sale'}</h2>
    <form id="saleForm" action="${pageContext.request.contextPath}${sale != null ? '/update-sale' : '/create-sale'}" method="post">
        <c:if test="${sale != null}">
            <!-- Hidden field for sale ID -->
            <input type="hidden" name="saleId" value="${sale.id}" />
        </c:if>
        <div class="form-group">
            <label for="customer">Select Customer:</label>
            <select class="form-control" id="customer" name="customerId" required>
                <option value="">Select Customer</option>
                <c:forEach var="customer" items="${customers}">
                    <option value="${customer.id}" <c:if test="${sale != null && customer.id == sale.customer.id}">selected</c:if>>${customer.firstName} ${customer.lastName}</option>
                </c:forEach>
            </select>
        </div>

        <h4 class="mt-4">Products</h4>
        <table class="table table-bordered" id="productTable">
            <thead>
                <tr>
                    <th>Product</th>
                    <th>Available Quantity</th>
                    <th>Price per Item</th>
                    <th>Quantity</th>
                    <th>Total Price</th>
                    <th><button type="button" class="btn btn-sm btn-success" id="addProduct"><i class="fas fa-plus"></i></button></th>
                </tr>
            </thead>
            <tbody>
                <c:choose>
                    <c:when test="${sale != null}">
                        <c:forEach var="item" items="${sale.saleItems}">
                            <tr>
                                <td>
                                    <select class="form-control product-select" name="productId[]" required>
                                        <option value="">Select Product</option>
                                        <c:forEach var="product" items="${products}">
                                            <option value="${product.id}" data-price="<fmt:formatNumber value='${product.price}' pattern='#.##'/>" <c:if test="${product.id == item.product.id}">selected</c:if>>${product.name}</option>
                                        </c:forEach>
                                    </select>
                                </td>
                                <td class="available-quantity">In Stock</td>
                                <td class="price-per-item">$<fmt:formatNumber value="${item.pricePerItem}" pattern="#.##"/></td>
                                <td>
                                    <input type="number" name="quantity[]" class="form-control quantity-input" min="1" value="${item.quantity}" required />
                                </td>
                                <td class="total-price">$<fmt:formatNumber value="${item.quantity * item.pricePerItem}" pattern="#.##"/></td>
                                <td><button type="button" class="btn btn-sm btn-danger removeProduct"><i class="fas fa-trash-alt"></i></button></td>
                            </tr>
                        </c:forEach>
                    </c:when>
                    <c:otherwise>
                        <!-- First Row for Creating New Sale -->
                        <tr>
                            <td>
                                <select class="form-control product-select" name="productId[]" required>
                                    <option value="">Select Product</option>
                                    <c:forEach var="product" items="${products}">
                                        <option value="${product.id}" data-price="<fmt:formatNumber value='${product.price}' pattern='#.##'/>">${product.name}</option>
                                    </c:forEach>
                                </select>
                            </td>
                            <td class="available-quantity">N/A</td>
                            <td class="price-per-item">N/A</td>
                            <td>
                                <input type="number" name="quantity[]" class="form-control quantity-input" min="1" value="1" required />
                            </td>
                            <td class="total-price">N/A</td>
                            <td><button type="button" class="btn btn-sm btn-danger removeProduct"><i class="fas fa-trash-alt"></i></button></td>
                        </tr>
                    </c:otherwise>
                </c:choose>
                <!-- Hidden row template for adding new products -->
                <tr id="productRowTemplate" style="display: none;">
                    <td>
                        <select class="form-control product-select" name="productId[]" disabled>
                            <option value="">Select Product</option>
                            <c:forEach var="product" items="${products}">
                                <option value="${product.id}" data-price="<fmt:formatNumber value='${product.price}' pattern='#.##'/>">${product.name}</option>
                            </c:forEach>
                        </select>
                    </td>
                    <td class="available-quantity">N/A</td>
                    <td class="price-per-item">N/A</td>
                    <td>
                        <input type="number" name="quantity[]" class="form-control quantity-input" min="1" value="1" disabled/>
                    </td>
                    <td class="total-price">N/A</td>
                    <td><button type="button" class="btn btn-sm btn-danger removeProduct"><i class="fas fa-trash-alt"></i></button></td>
                </tr>
            </tbody>
        </table>

        <div class="text-right">
            <h4>Total Amount: <span id="totalAmount">$<c:choose><c:when test="${sale != null}"><fmt:formatNumber value="${sale.totalAmount}" pattern="#.##"/></c:when><c:otherwise>0.00</c:otherwise></c:choose></span></h4>
        </div>

        <button type="submit" class="btn btn-primary">${sale != null ? 'Update Sale' : 'Create Sale'}</button>
    </form>
</div>

<!-- jQuery first, then Bootstrap JS -->
<script src="https://code.jquery.com/jquery-3.6.0.min.js"></script>
<!-- Include Popper.js and Bootstrap JS -->
<script src="https://cdnjs.cloudflare.com/ajax/libs/popper.js/1.12.9/umd/popper.min.js"></script>
<script src="https://maxcdn.bootstrapcdn.com/bootstrap/4.0.0/js/bootstrap.min.js"></script>

<!-- Your custom script -->
<script>
$(document).ready(function() {
    function updateTotalPrice(row) {
        var pricePerItem = parseFloat(row.find('.price-per-item').text().replace('$', '')) || 0;
        var quantity = parseInt(row.find('.quantity-input').val()) || 0;
        var totalPrice = pricePerItem * quantity;
        row.find('.total-price').text('$' + totalPrice.toFixed(2));
        updateTotalAmount();
    }

    function updateTotalAmount() {
        var totalAmount = 0;
        $('#productTable tbody tr').each(function() {
            var totalPrice = parseFloat($(this).find('.total-price').text().replace('$', '')) || 0;
            totalAmount += totalPrice;
        });
        $('#totalAmount').text('$' + totalAmount.toFixed(2));
    }

    // Update price and available quantity when product is selected
    $(document).on('change', '.product-select', function() {
        var row = $(this).closest('tr');
        var selectedOption = $(this).find('option:selected');
        var priceStr = selectedOption.attr('data-price');
        var price = parseFloat(priceStr) || 0;
        // Assume available quantity is always sufficient for this example
        row.find('.available-quantity').text('In Stock');
        row.find('.price-per-item').text('$' + price.toFixed(2));
        updateTotalPrice(row);
    });

    // Update total price when quantity changes
    $(document).on('input', '.quantity-input', function() {
        var row = $(this).closest('tr');
        updateTotalPrice(row);
    });

    // Add new product row
    $('#addProduct').click(function() {
	    var newRow = $('#productRowTemplate')
	        .clone(true)
	        .removeAttr('id')
	        .removeAttr('style');
	
	    // Enable inputs
	    newRow.find('input, select').prop('disabled', false);
	
	    newRow.find('input, select').val('');
	    newRow.find('.available-quantity, .price-per-item, .total-price').text('N/A');
	    $('#productTable tbody').append(newRow);
	});

    // Remove product row
    $(document).on('click', '.removeProduct', function() {
        $(this).closest('tr').remove();
        updateTotalAmount();
    });
    
    $('#saleForm').on('submit', function(event) {
        console.log('Form is being submitted');
    });

    // Recalculate total amount on page load (especially important when editing)
    updateTotalAmount();
});

console.log('Form action:', document.getElementById('saleForm').action);
</script>

</body>
</html>
