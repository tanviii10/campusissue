function login(){

    let email = document.getElementById("email").value;
    let password = document.getElementById("password").value;

    fetch("/auth/login", {
        method: "POST",
        headers: {
            "Content-Type": "application/json"
        },
        body: JSON.stringify({
            email: email,
            password: password
        })
    })
    .then(res => res.json())
    .then(user => {

        if(user.role === "ADMIN"){
            window.location.href = "/admin-dashboard.html";
        }
        else{
            window.location.href = "/student-dashboard.html";
        }

    })
}