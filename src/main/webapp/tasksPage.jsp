<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Tasks</title>
</head>
<body>

<h2>Add Task</h2>
<form action="${pageContext.request.contextPath}/tasks" method="post">
    <input type="hidden" name="action" value="add">
    Title: <input type="text" name="title" required><br>
    User ID: <input type="text" name="id" required><br>
    <input type="submit" value="Add">
</form>


<h2>All Tasks</h2>


<table border="1">
    <tr>
        <th>ID</th>
        <th>Title</th>
        <th>User_Id</th>

    </tr>
    <c:forEach var="task" items="${tasks}">
        <tr>
            <td><c:out value="${task.id}" /></td>
            <td><c:out value="${task.title}" /></td>
            <td><c:out value="${task.user.id}" /></td>
        </tr>
    </c:forEach>
</table>


<h2>Update</h2>
<form action="${pageContext.request.contextPath}/tasks" method="post">
    <input type="hidden" name="action" value="update">
    ID: <input type="text" name="id" required><br>
    new Title: <input type="text" name="title" required><br>
    new User (id): <input type="text" name="user" required><br>
    <input type="submit" value="Update">
</form>


<h2>Delete Task</h2>
<form action="${pageContext.request.contextPath}/tasks" method="post">
    <input type="hidden" name="action" value="delete">
    ID: <input type="text" name="id" required><br>
    <input type="submit" value="Delete">
</form>

<p style="text-align: center;">
    <a href="${pageContext.request.contextPath}/index.jsp">Back</a>
</p>

</body>
</html>
