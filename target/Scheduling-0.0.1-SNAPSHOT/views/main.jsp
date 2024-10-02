<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Main Page</title>
</head>
<body>
    <h1>Welcome to the Management System</h1>

    <p>Select a section to manage:</p>

    <ul>
        <li><a href="${pageContext.request.contextPath}/categories">Manage Categories</a></li>
        <li><a href="${pageContext.request.contextPath}/products">Manage Products</a></li>
        <li><a href="${pageContext.request.contextPath}/sales">Manage Sales</a></li>
        <li><a href="${pageContext.request.contextPath}/customers">Manage Customers</a></li>
    </ul>
</body>
</html>
