<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ page import="model.Customer" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>Customer Form</title>

<!-- Bootstrap CSS -->
<link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/4.0.0/css/bootstrap.min.css">

</head>
<body>

<div class="container mt-2">
    <h2 class="mb-4">Customer Form</h2>

    <c:if test="${param.error != null}">
        <div class="alert alert-danger" role="alert">
            ${param.error}
        </div>
    </c:if>
	
	<c:if test="${successMessage != null}">
	    <div class="alert alert-success" role="alert">
	        ${successMessage}
	    </div>
	</c:if>
	
	
    <form action="${pageContext.request.contextPath}/customer" method="post">
        <!-- Search Customer by ID -->
        <div class="form-group">
            <label for="searchCustomerId">Search Customer by ID:</label>
            <div class="input-group">
                <input type="number" class="form-control" id="searchCustomerId" name="searchCustomerId" placeholder="Enter Customer ID">
                <div class="input-group-append">
                    <button type="submit" name="action" value="search" class="btn btn-outline-secondary">Search</button>
                </div>
            </div>
        </div>
    </form>

    <hr>

    <!-- Customer Details Form -->
    <form action="${pageContext.request.contextPath}/customer" method="post">
        <c:if test="${customer != null}">
            <!-- Hidden field for customer ID -->
            <input type="hidden" name="customerId" value="${customer.id}" />
        </c:if>

        <div class="form-group">
            <label for="firstName">First Name:</label>
            <input type="text" class="form-control" id="firstName" name="firstName" value="${customer != null ? customer.firstName : ''}" required>
        </div>

        <div class="form-group">
            <label for="lastName">Last Name:</label>
            <input type="text" class="form-control" id="lastName" name="lastName" value="${customer != null ? customer.lastName : ''}" required>
        </div>

        <div class="form-group">
            <label for="email">Email:</label>
            <input type="email" class="form-control" id="email" name="email" value="${customer != null ? customer.email : ''}" required>
        </div>

        <div class="form-group">
            <label for="phoneNumber">Phone Number:</label>
            <input type="text" class="form-control" id="phoneNumber" name="phoneNumber" value="${customer != null ? customer.phoneNumber : ''}" required>
        </div>

        <div class="form-group">
            <label for="address">Address:</label>
            <textarea class="form-control" id="address" name="address" rows="3" required>${customer != null ? customer.address : ''}</textarea>
        </div>

        <button type="submit" name="action" value="${customer != null ? 'update' : 'create'}" class="btn btn-primary">
            ${customer != null ? 'Update Customer' : 'Create Customer'}
        </button>
    </form>
</div>

<!-- jQuery and Bootstrap JS -->
<script src="https://code.jquery.com/jquery-3.6.0.min.js"></script>
<!-- Include Popper.js and Bootstrap JS -->
<script src="https://cdnjs.cloudflare.com/ajax/libs/popper.js/1.12.9/umd/popper.min.js"></script>
<script src="https://maxcdn.bootstrapcdn.com/bootstrap/4.0.0/js/bootstrap.min.js"></script>
</body>
</html>
