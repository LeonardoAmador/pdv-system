<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Create Category</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.1/dist/css/bootstrap.min.css" rel="stylesheet">
    
    <style>
        .fade-out {
            transition: opacity 0.5s ease-out;
            opacity: 0;
        }
    </style>
</head>
<body>

    <div class="container mt-5">
    
        <h2 class="mb-4">Create a New Product Category</h2>

        <c:if test="${not empty success}">
            <div class="alert alert-success alert-dismissible fade show" role="alert">
                ${success}
                <button type="button" class="btn-close" data-bs-dismiss="alert" aria-label="Close"></button>
            </div>
        </c:if>
        <c:if test="${not empty error}">
            <div class="alert alert-danger alert-dismissible fade show" role="alert">
                ${error}
                <button type="button" class="btn-close" data-bs-dismiss="alert" aria-label="Close"></button>
            </div>
        </c:if>

        <form action="${pageContext.request.contextPath}/create-category" method="POST" class="mt-3">
            <div class="mb-3">
                <label for="name" class="form-label">Category Name</label>
                <input type="text" class="form-control" id="name" name="name" placeholder="Enter category name" required>
            </div>
            <div class="mb-3">
                <label for="description" class="form-label">Category Description</label>
                <textarea class="form-control" id="description" name="description" placeholder="Enter category description" rows="3" required></textarea>
            </div>
            <button type="submit" class="btn btn-primary">Create Category</button>
        </form>
    </div>

    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.1/dist/js/bootstrap.bundle.min.js"></script>

    <script>
        document.addEventListener('DOMContentLoaded', function () {
            const alerts = document.querySelectorAll('.alert');
            if (alerts.length > 0) {
                setTimeout(function () {
                    alerts.forEach(alert => {
                        alert.classList.add('fade-out');
                        setTimeout(() => {
                            alert.remove();
                        }, 500);
                    });
                }, 3000);
            }
        });
    </script>
</body>
</html>
