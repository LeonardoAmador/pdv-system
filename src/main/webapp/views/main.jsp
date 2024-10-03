<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Main Page</title>

    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.1/dist/css/bootstrap.min.css" rel="stylesheet">
    <link href="https://cdnjs.cloudflare.com/ajax/libs/bootstrap-icons/1.10.0/font/bootstrap-icons.min.css" rel="stylesheet">
    
    <link href="${pageContext.request.contextPath}/css/custom.css" rel="stylesheet">
</head>
<body>

    <jsp:include page="/WEB-INF/jsp/header.jsp" />

    <div class="d-flex">
        <jsp:include page="/WEB-INF/jsp/sidebar.jsp" />

        <jsp:include page="/WEB-INF/jsp/content.jsp" />
    </div>

    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.1/dist/js/bootstrap.bundle.min.js"></script>

    <script>
        const sidebar = document.getElementById('sidebar');
        const toggleButton = document.getElementById('sidebarToggle');

        toggleButton.addEventListener('click', () => {
            sidebar.classList.toggle('collapsed');
        });
    </script>
</body>
</html>
