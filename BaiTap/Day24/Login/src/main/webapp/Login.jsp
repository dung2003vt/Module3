<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<!DOCTYPE HTML>
<html>
<link rel="stylesheet" href="Login.css">
<body>
<form action="/login" method="post">
    <div class="login">
        <h2>Login</h2>
        <input type="text" name="username" size="30"  placeholder="username" />
        <input type="password" name="password" size="30" placeholder="password" />
        <input type="submit" value="Sign in"/>
    </div>
</form>
</body>
</html>