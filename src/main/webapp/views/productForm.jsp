	<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	    pageEncoding="ISO-8859-1"%>
	<%@ page import="java.util.List, model.Category, model.Product" %>
	<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
	
	<!DOCTYPE html>
	<html>
	<head>
	<meta charset="ISO-8859-1">
	<title>${product != null ? 'Edit Product' : 'Register Product'}</title>
	
	<link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/4.0.0/css/bootstrap.min.css">
	
	</head>
	<body>
	
	<div class="container mt-5">
	    <c:if test="${param.success == 'true'}">
	        <div class="alert alert-success mb-2" role="alert">
	            Product registered successfully!
	        </div>
	    </c:if>
	
	    <c:if test="${param.updateSuccess == 'true'}">
	        <div class="alert alert-success mb-2" role="alert">
	            Product updated successfully!
	        </div>
	    </c:if>
	
	    <h2 class="mb-4">${product != null ? 'Edit Product' : 'Register New Product'}</h2>
	    <form action="${pageContext.request.contextPath}${product != null ? '/update-product' : '/create-product'}" method="post">
	        <c:if test="${product != null}">
	            <!-- Hidden field for product ID -->
	            <input type="hidden" name="id" value="${product.id}" />
	        </c:if>
	        <div class="form-group">
	            <label for="name">Product Name:</label>
	            <input type="text" class="form-control" id="name" name="name" placeholder="Enter product name"
	                value="${product != null ? product.name : ''}" required>
	        </div>
	
	        <div class="form-group">
	            <label for="price">Price:</label>
	            <input type="number" step="0.01" class="form-control" id="price" name="price" placeholder="Enter price"
	                value="${product != null ? product.price : ''}" required>
	        </div>
	
	        <div class="form-group">
	            <label for="description">Description:</label>
	            <textarea class="form-control" id="description" name="description" rows="3" placeholder="Enter description" required>${product != null ? product.description : ''}</textarea>
	        </div>
	
	        <div class="form-group">
	            <label for="category">Category:</label>
	            <select class="form-control" id="category" name="categoryId" required>
	                <option value="">Select Category</option>
	                <c:forEach var="category" items="${categories}">
	                    <option value="${category.id}" ${product != null && product.category.id == category.id ? 'selected' : ''}>${category.name}</option>
	                </c:forEach>
	            </select>
	        </div>
	
	        <button type="submit" class="btn btn-primary">${product != null ? 'Update Product' : 'Register Product'}</button>
	    </form>
	</div>
	
	<script src="https://code.jquery.com/jquery-3.2.1.slim.min.js"></script>
	<script src="https://cdnjs.cloudflare.com/ajax/libs/popper.js/1.12.9/umd/popper.min.js"></script>
	<script src="https://maxcdn.bootstrapcdn.com/bootstrap/4.0.0/js/bootstrap.min.js"></script>
	</body>
	</html>
