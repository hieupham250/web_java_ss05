<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
  <title>Đăng Nhập</title>
</head>
<body>
<h2>Đăng Nhập</h2>
<form action="<%= request.getContextPath() %>/login" method="post">
  Tên người dùng: <input type="text" name="username" required><br><br>
  Mật khẩu: <input type="password" name="password" required><br><br>
  <input type="submit" value="Đăng Nhập">
</form>
</body>
</html>
