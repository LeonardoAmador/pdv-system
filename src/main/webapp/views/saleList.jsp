<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ page import="java.util.List, model.Sale, model.SaleItem" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>

<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>Sale List</title>

<!-- Bootstrap CSS -->
<link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/4.0.0/css/bootstrap.min.css">

<!-- Font Awesome for Icons -->
<link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/5.15.3/css/all.min.css">

</head>
<body>

<div class="container mt-5">
    <div class="d-flex justify-content-between align-items-center mb-4">
        <h2>Sale List</h2>
        <a href="${pageContext.request.contextPath}/create-sale" class="btn btn-primary">
            <i class="fas fa-plus"></i> Add New Sale
        </a>
    </div>

    <!-- Success and Error Messages -->
    <c:if test="${param.success == 'true'}">
        <div class="alert alert-success" role="alert">
            Sale created successfully!
        </div>
    </c:if>
    <c:if test="${param.updateSuccess == 'true'}">
        <div class="alert alert-success" role="alert">
            Sale updated successfully!
        </div>
    </c:if>
    <c:if test="${param.deleteSuccess == 'true'}">
        <div class="alert alert-success" role="alert">
            Sale deleted successfully!
        </div>
    </c:if>
    <c:if test="${param.deleteSuccess == 'false'}">
        <div class="alert alert-danger" role="alert">
            Failed to delete the sale.
        </div>
    </c:if>

    <!-- Sales Table -->
    <table class="table table-bordered table-hover">
        <thead class="thead-dark">
            <tr>
                <th>Sale ID</th>
                <th>Date</th>
                <th>Customer</th>
                <th>Total Amount</th>
                <th style="width: 150px;">Actions</th>
            </tr>
        </thead>
        <tbody>
            <c:forEach var="sale" items="${sales}">
                <tr>
                    <td>${sale.id}</td>
                    <td><fmt:formatDate value="${sale.saleDate}" pattern="yyyy-MM-dd HH:mm:ss" /></td>
                    <td>${sale.customer.firstName} ${sale.customer.lastName}</td>
                    <td><fmt:formatNumber value="${sale.totalAmount}" type="currency" currencySymbol="$" /></td>
                    <td>
        
                        <a href="${pageContext.request.contextPath}/edit-sale?id=${sale.id}" class="btn btn-sm btn-primary">
                            <i class="fas fa-edit"></i>
                        </a>
                       
         
                        <a href="${pageContext.request.contextPath}/delete-sale?id=${sale.id}" class="btn btn-sm btn-danger" onclick="return confirm('Are you sure you want to delete this sale?');">
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
