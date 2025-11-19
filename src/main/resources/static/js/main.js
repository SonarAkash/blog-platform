document.addEventListener("DOMContentLoaded", () => {
    checkAuth();
});


function checkAuth() {
    const token = localStorage.getItem("jwtToken");
    const userStr = localStorage.getItem("user");
    
    const avatarImgs = document.querySelectorAll(".avatar img"); 
    const navGuest = document.getElementById("nav-guest");
    const navUser = document.querySelectorAll(".nav__profile"); 

    if (token && userStr) {
        const user = JSON.parse(userStr);
        
        if (navGuest) navGuest.style.display = "none";
        navUser.forEach(el => {
            el.style.display = "flex";
            el.classList.remove("hidden");
        });

        avatarImgs.forEach(img => {
            img.src = user.avatar || "https://cdn-icons-png.flaticon.com/512/149/149071.png";
        });
    } else {
        if (navGuest) navGuest.style.display = "block";
        navUser.forEach(el => el.style.display = "none");
    }
}

function logout() {
    localStorage.removeItem("jwtToken");
    localStorage.removeItem("user");
    window.location.href = "signin.html";
}

function showToast(message, type = 'info') {
    let container = document.getElementById('toast-container');
    if (!container) {
        container = document.createElement('div');
        container.id = 'toast-container';
        document.body.appendChild(container);
    }

    const toast = document.createElement('div');
    toast.className = `toast ${type}`;
    toast.innerHTML = `
        <span>${message}</span>
        <span onclick="this.parentElement.remove()" style="cursor:pointer; margin-left:10px;">&times;</span>
    `;

    container.appendChild(toast);

    setTimeout(() => {
        toast.style.animation = 'fadeOut 0.5s ease forwards';
        setTimeout(() => toast.remove(), 500);
    }, 4000);
}


function handleSearch(e) {
    e.preventDefault();
    const query = document.querySelector(".search__bar input").value.toLowerCase();
    const articles = document.querySelectorAll(".post");
    
    articles.forEach(article => {
        const title = article.querySelector(".post__tittle").innerText.toLowerCase();
        const body = article.querySelector(".post_body").innerText.toLowerCase();
        
        if (title.includes(query) || body.includes(query)) {
            article.style.display = "block";
        } else {
            article.style.display = "none";
        }
    });
}


function createPostHTML(post) {
    const date = new Date(post.dateTime).toLocaleString();
    
    const bodyPreview = post.body.length > 150 ? post.body.substring(0, 150) + "..." : post.body;

    return `
    <article class="post">
        <div class="post__thumbnail">
            <img src="${post.thumbnail}">
        </div>
        <div class="post__info">
            <a href="category-posts.html?id=${post.category.id}" class="branch__button">${post.category.title}</a>
            <h3 class="post__tittle">
                <a href="post.html?id=${post.id}" target="_blank">${post.title}</a>
            </h3>
            <p class="post_body">${bodyPreview}</p>
            <div class="post__author">
                <div class="post__author-avatar">
                    <img src="${post.author.avatar || 'https://cdn-icons-png.flaticon.com/512/149/149071.png'}">
                </div>
                <div class="post__author-info">
                    <h5>By: ${post.author.firstname} ${post.author.lastname}</h5>
                    <small>${date}</small>
                </div>
            </div>
        </div>
    </article>
    `;
}