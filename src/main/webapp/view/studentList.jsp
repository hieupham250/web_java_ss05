<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page import="com.example.ss05.model.Student" %>
<%@ page import="java.util.List" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%
    List<Student> students = (List<Student>) request.getAttribute("students");
    int currentPage = (request.getAttribute("currentPage") != null) ? (Integer) request.getAttribute("currentPage") : 1;
    int totalPages = (request.getAttribute("totalPages") != null) ? (Integer) request.getAttribute("totalPages") : 1;

%>
<html>
<head>
    <title>Danh sách sinh viên</title>
</head>
<body>
<a href="<%= request.getContextPath() %>/view/studentForm.jsp">
    <button>Thêm Sinh Viên</button>
</a>
<h2>Danh sách sinh viên</h2>
<table border="1" cellpadding="5">
    <tr>
        <th>ID</th><th>Họ tên</th><th>Tuổi</th><th>Địa chỉ</th><th>Hành động</th>
    </tr>
    <c:forEach var="s" items="${students}">
        <tr>
            <td>${s.id}</td>
            <td>${s.name}</td>
            <td>${s.age}</td>
            <td>${s.address}</td>
            <td>
                <form action="${pageContext.request.contextPath}/studentController" method="get">
                    <input type="hidden" name="id" value="${s.id}">
                    <input type="hidden" name="action" value="edit">
                    <input type="submit" value="Sửa">
                </form>
                <form action="${pageContext.request.contextPath}/studentController" method="get"
                      onsubmit="return confirm('Bạn chắc chắn muốn xóa sinh viên này?');">
                    <input type="hidden" name="id" value="${s.id}">
                    <input type="hidden" name="action" value="delete">
                    <input type="submit" value="Xóa">
                </form>
            </td>
        </tr>
    </c:forEach>
</table>

<!-- Phân trang -->
<div>
    <span>Trang <%= currentPage %> của <%= totalPages %></span>
</div>
<div>
    <form action="<%= request.getContextPath() %>/studentController" method="get">
        <input type="hidden" name="page" value="<%= currentPage - 1 > 0 ? currentPage - 1 : 1 %>">
        <input type="submit" value="Trước" <%= currentPage == 1 ? "disabled" : "" %> />
    </form>
    <form action="<%= request.getContextPath() %>/studentController" method="get">
        <input type="hidden" name="page" value="<%= currentPage + 1 <= totalPages ? currentPage + 1 : totalPages %>">
        <input type="submit" value="Tiếp Theo" <%= currentPage == totalPages ? "disabled" : "" %> />
    </form>
</div>
</body>
</html>
