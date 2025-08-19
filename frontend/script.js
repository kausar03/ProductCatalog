const apiUrl = "http://localhost:8080/api/products"; // Your backend URL

// Fetch and display all products
function formatPrice(price) {
    return new Intl.NumberFormat('en-IN', { style: 'currency', currency: 'INR' }).format(price);
}

async function fetchProducts() {
    try {
        const response = await fetch(apiUrl);
        if (!response.ok) throw new Error("Unable to fetch products");

        const products = await response.json();
        renderProducts(products);
    } catch (error) {
        console.error("Error fetching products:", error);
    }
}

// Render products into table
function renderProducts(products) {
    const tableBody = document.getElementById("productsBody");
    tableBody.innerHTML = ""; // Clear existing rows

    products.forEach(product => {
        const row = document.createElement("tr");

        row.innerHTML = `
            <td>${product.id}</td>
            <td>${product.name}</td>
            <td>${product.description}</td>
            <td>${formatPrice(product.price)}</td>
            <td>${product.quantity}</td>
            <td>
                <button onclick="deleteProduct(${product.id})">Delete</button>
            </td>
        `;

        tableBody.appendChild(row);
    });
}

// Add product
document.getElementById("productForm").addEventListener("submit", async (e) => {
    e.preventDefault();

    const newProduct = {
        name: document.getElementById("nameInput").value,
        description: document.getElementById("descInput").value,
        price: parseFloat(document.getElementById("priceInput").value),
        quantity: parseInt(document.getElementById("qtyInput").value)
    };

    try {
        const response = await fetch(apiUrl, {
            method: "POST",
            headers: { "Content-Type": "application/json" },
            body: JSON.stringify(newProduct)
        });

        if (response.ok) {
            document.getElementById("productForm").reset();
            fetchProducts(); // Refresh product list
        } else {
            console.error("Failed to add product");
        }
    } catch (error) {
        console.error("Error adding product:", error);
    }
});

// Delete product
async function deleteProduct(id) {
    try {
        const response = await fetch(`${apiUrl}/${id}`, { method: "DELETE" });
        if (response.ok) {
            fetchProducts(); // Refresh after delete
        } else {
            console.error("Failed to delete product");
        }
    } catch (error) {
        console.error("Error deleting product:", error);
    }
}

// Search product by name
document.getElementById("searchBtn").addEventListener("click", async () => {
    const query = document.getElementById("searchInput").value.trim();

    if (!query) {
        fetchProducts(); // If empty search, load all
        return;
    }

    try {
        const response = await fetch(`${apiUrl}/search?name=${query}`);
        if (!response.ok) throw new Error("Unable to search products");

        const products = await response.json();
        renderProducts(products);
    } catch (error) {
        console.error("Error searching products:", error);
    }
});

// View all products button
document.getElementById("viewBtn").addEventListener("click", fetchProducts);

// Load products on page load
window.onload = fetchProducts;
