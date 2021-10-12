<%@ page import="me.ulearn.DirectoryParser" %>
<%@ page import="me.ulearn.FileObject" %>
<%@ page import="java.text.SimpleDateFormat" %>
<%@ page import="java.util.Date" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
  <title>FileExplorer – Log In</title>
  <link rel="stylesheet" href="static/css/style.css"/>
</head>
<body>
<div class="container">
  <div class="user_form">
    <div class="title">
      <h1>Log in now</h1>
    </div>
    <div class="files_body">
      <div class="files_list">
        <form method="POST">
          <div class="label_input">
            <label for="login_input">Your login</label>
            <input id="login_input" type="text" placeholder="Enter your login" class="input" />
          </div>

          <div class="label_input">
            <label for="password_input">Your password</label>
            <input id="password_input" type="password" placeholder="Enter your password" class="input" />
          </div>

          <button type="submit" class="button_grey">Submit form</button>
          <a class="bottom_link" href="/signup">I don't have an account yet</a>
        </form>
      </div>
    </div>
  </div>
</div>
</body>
</html>
