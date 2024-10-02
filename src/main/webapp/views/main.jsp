<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Main Page</title>

    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.1/dist/css/bootstrap.min.css" rel="stylesheet">
</head>
<body>
    <div class="container">
        <h1 class="text-center mt-4">Welcome to the Management System</h1>
        <p>Select a section to manage:</p>

        <ul class="list-group">
            <li class="list-group-item"><a href="${pageContext.request.contextPath}/categories">Manage Categories</a></li>
            <li class="list-group-item"><a href="${pageContext.request.contextPath}/products">Manage Products</a></li>
            <li class="list-group-item"><a href="${pageContext.request.contextPath}/sales">Manage Sales</a></li>
            <li class="list-group-item"><a href="${pageContext.request.contextPath}/customers">Manage Customers</a></li>
        </ul>
    </div>

    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.1/dist/js/bootstrap.bundle.min.js"></script>
</body>
</html>
