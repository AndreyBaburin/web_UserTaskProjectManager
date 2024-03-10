<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>All Users</title>
</head>
<body>


<h2>Add User</h2>
<form action="${pageContext.request.contextPath}/users" method="post">
    <input type="hidden" name="action" value="add">
    Name: <input type="text" name="name" required><br>
    Email: <input type="text" name="email" required><br>
    <input type="submit" value="Add">
</form>


<h2>All Users</h2>


<table border="1">
    <tr>
        <th>ID</th>
        <th>Name</th>
        <th>Email</th>

    </tr>
    <c:forEach var="user" items="${users}">
        <tr>
            <td><c:out value="${user.id}" /></td>
            <td><c:out value="${user.name}" /></td>
            <td><c:out value="${user.email}" /></td>
        </tr>
    </c:forEach>
</table>


<h2>Update</h2>
<form action="${pageContext.request.contextPath}/users" method="post">
    <input type="hidden" name="action" value="update">
    ID: <input type="text" name="id" required><br>
    new Name: <input type="text" name="newName" required><br>
    new Email: <input type="text" name="newEmail" required><br>
    <input type="submit" value="Update">
</form>


<h2>Delete User</h2>
<form action="${pageContext.request.contextPath}/users" method="post">
    <input type="hidden" name="action" value="delete">
    ID: <input type="text" name="id" required><br>
    <input type="submit" value="Delete">
</form>

<p style="text-align: center;">
    <a href="${pageContext.request.contextPath}/index.jsp">Back</a>
</p>

</body>
</html>