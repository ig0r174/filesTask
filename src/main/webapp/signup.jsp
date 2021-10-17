<%@ page import="me.ulearn.DirectoryParser" %>
<%@ page import="me.ulearn.FileObject" %>
<%@ page import="java.text.SimpleDateFormat" %>
<%@ page import="java.util.Date" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>FileExplorer – Sign Up</title>
    <link rel="stylesheet" href="static/css/style.css"/>
    <script src="static/js/form.js"></script>
</head>
<body>
<div class="container">
    <div class="user_form">
        <div class="title">
            <h1>Sign up now</h1>
        </div>
        <div class="files_body">
            <div class="files_list">
                <form action="/api/signup" method="POST" enctype="application/x-www-form-urlencoded">
                    <div class="label_input">
                        <label for="login_input">Your login</label>
                        <input name="login" id="login_input" type="text" placeholder="Enter your login" class="input" />
                    </div>

                    <div class="label_input">
                        <label for="email_input">Your Email</label>
                        <input name="email" id="email_input" type="text" placeholder="Enter your e-mail" class="input" />
                    </div>

                    <div class="label_input">
                        <label for="password_input">Your password</label>
                        <input name="pass" id="password_input" type="password" placeholder="Enter your password" class="input" />
                    </div>

                    <button type="submit" class="button_grey">Submit form</button>
                    <a class="bottom_link" href="/login">I have an account already</a>
                </form>
            </div>
        </div>
    </div>
</div>
</body>
</html>
