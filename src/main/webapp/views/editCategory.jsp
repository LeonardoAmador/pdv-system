<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>Edit Category</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.1/dist/css/bootstrap.min.css" rel="stylesheet">
</head>
<body>

    <div class="container mt-5">
        <h2>Edit Category</h2>
        <form action="${pageContext.request.contextPath}/update-category" method="POST">
            <input type="hidden" name="id" value="${category.id}">
            <div class="mb-3">
                <label for="name" class="form-label">Category Name</label>
                <input type="text" class="form-control" id="name" name="name" value="${category.name}" required>
            </div>
            <div class="mb-3">
                <label for="description" class="form-label">Category Description</label>
                <textarea class="form-control" id="description" name="description" rows="3" required>${category.description}</textarea>
            </div>
            <button type="submit" class="btn btn-primary">Update Category</button>
        </form>
    </div>

    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.1/dist/js/bootstrap.bundle.min.js"></script>
</body>
</html>
