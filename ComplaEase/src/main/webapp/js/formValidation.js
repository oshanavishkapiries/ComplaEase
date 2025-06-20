function validateLoginForm() {
    var username = document.forms["loginForm"]["username"].value;
    var password = document.forms["loginForm"]["password"].value;
    if (username === "" || password === "") {
        alert("Username and password are required.");
        return false;
    }
    return true;
}

function validateComplaintForm() {
    var title = document.forms["complaintForm"]["title"].value;
    var description = document.forms["complaintForm"]["description"].value;
    if (title === "" || description === "") {
        alert("Title and description are required.");
        return false;
    }
    return true;
}

function validateEditForm() {
    var title = document.forms["editForm"]["title"].value;
    var description = document.forms["editForm"]["description"].value;
    if (title === "" || description === "") {
        alert("Title and description are required.");
        return false;
    }
    return true;
} 