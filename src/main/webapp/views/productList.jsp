<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ page import="java.util.List, model.Product" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>

<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>Product List</title>

<!-- Bootstrap CSS -->
<link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/4.0.0/css/bootstrap.min.css">

<!-- Font Awesome for Icons -->
<link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/5.15.3/css/all.min.css">

</head>
<body>

<div class="container mt-5">
    <div class="d-flex justify-content-between align-items-center mb-4">
        <h2>Product List</h2>
        <a href="${pageContext.request.contextPath}/create-product" class="btn btn-primary">
            <i class="fas fa-plus"></i> Add New Product
        </a>
    </div>
    
    <c:if test="${param.updateSuccess == 'true'}">
	    <div class="alert alert-success" role="alert">
	        Product updated successfully!
	    </div>
	</c:if>
	<c:if test="${param.updateSuccess == 'false'}">
	    <div class="alert alert-danger" role="alert">
	        Failed to update the product.
	    </div>
	</c:if>
	    

    <c:if test="${param.success == 'true'}">
        <div class="alert alert-success" role="alert">
            Product registered successfully!
        </div>
    </c:if>
    
    <c:if test="${param.deleteSuccess == 'true'}">
	    <div class="alert alert-success" role="alert">
	        Product deleted successfully!
	    </div>
	</c:if>
	<c:if test="${param.deleteSuccess == 'false'}">
	    <div class="alert alert-danger" role="alert">
	        Failed to delete the product.
	    </div>
	</c:if>
	    

    <table class="table table-bordered table-hover">
        <thead class="thead-dark">
            <tr>
                <th>ID</th>
                <th>Name</th>
                <th>Price</th>
                <th>Description</th>
                <th>Category</th>
                <th style="width: 100px;">Actions</th>
            </tr>
        </thead>
        <tbody>
            <c:forEach var="product" items="${products}">
                <tr>
                    <td>${product.id}</td>
                    <td>${product.name}</td>
                    <td><fmt:formatNumber value="${product.price}" type="currency" currencySymbol="$" /></td>
                    <td>${product.description}</td>
                    <td>${product.category.name}</td>
                    <td>
                        <a href="${pageContext.request.contextPath}/edit-product?id=${product.id}" class="btn btn-sm btn-primary">
                            <i class="fas fa-edit"></i>
                        </a>
                        <a href="${pageContext.request.contextPath}/delete-product?id=${product.id}" class="btn btn-sm btn-danger">
                            <i class="fas fa-trash-alt"></i>
                        </a>
                    </td>
                </tr>
            </c:forEach>
        </tbody>
    </table>
</div>

<!-- Bootstrap JS and dependencies (Optional for Bootstrap components) -->
<script src="https://code.jquery.com/jquery-3.2.1.slim.min.js"></script>
<script src="https://cdnjs.cloudflare.com/ajax/libs/popper.js/1.12.9/umd/popper.min.js"></script>
<script src="https://maxcdn.bootstrapcdn.com/bootstrap/4.0.0/js/bootstrap.min.js"></script>
</body>
</html>
