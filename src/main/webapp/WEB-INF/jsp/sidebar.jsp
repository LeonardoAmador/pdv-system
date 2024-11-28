<nav id="sidebar" class="bg-dark collapsed">
    <ul class="nav flex-column">
        <li class="nav-item">
            <a class="nav-link text-light" href="#" onclick="loadPage('${pageContext.request.contextPath}/list-categories')">
                <i class="bi bi-list"></i> <span>Manage Categories</span>
            </a>
        </li>
        <li class="nav-item">
            <a class="nav-link text-light" href="#" onclick="loadPage('${pageContext.request.contextPath}/list-products')">
                <i class="bi bi-box"></i> <span>Manage Products</span>
            </a>
        </li>
        <li class="nav-item">
            <a class="nav-link text-light" href="#" onclick="loadPage('${pageContext.request.contextPath}/list-sales')">
                <i class="bi bi-cash"></i> <span>Manage Sales</span>
            </a>
        </li>
        <li class="nav-item">
            <a class="nav-link text-light" href="#" onclick="loadPage('${pageContext.request.contextPath}/customer')">
                <i class="bi bi-person"></i> <span>Manage Customers</span>
            </a>
        </li>
    </ul>
</nav>
