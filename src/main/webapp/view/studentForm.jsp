
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Thêm sinh viên</title>
</head>
<body>
<h2>Form Nhập Thông Tin Sinh Viên</h2>
<form action="<%= request.getContextPath() %>/studentController" method="post">
    Họ tên: <input type="text" name="name" required><br><br>
    Tuổi: <input type="number" name="age" required><br><br>
    Địa chỉ: <input type="text" name="address" required><br><br>
    <input type="submit" value="Gửi">
</form>
</body>
</html>
