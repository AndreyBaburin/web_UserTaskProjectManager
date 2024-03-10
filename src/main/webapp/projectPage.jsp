<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Project</title>
</head>
<body>

<h2>Add Project</h2>
<h4>if you want to skip a line, add "0"</h4>
<form action="${pageContext.request.contextPath}/projects" method="post">
    <input type="hidden" name="action" value="add">
    Definition: <input type="text" name="definition" required><br>
    Task ID: <input type="text" name="task1" required><br>
    Task ID: <input type="text" name="task2" required><br>
    Task ID: <input type="text" name="task3" required><br>
    Task ID: <input type="text" name="task4" required><br>
    Task ID: <input type="text" name="task5" required><br>
    <input type="submit" value="Add">
</form>


<h2>All Projects</h2>


<table border="1">
    <tr>
        <th>ID</th>
        <th>Definition</th>
    </tr>
    <c:forEach var="project" items="${projects}">
        <tr>
            <td><c:out value="${project.id}" /></td>
            <td><c:out value="${project.definition}" /></td>
        </tr>
    </c:forEach>
</table>

<h2>Show Tasks of Project</h2>
<form action="${pageContext.request.contextPath}/projects" method="post">
    <input type="hidden" name="action" value="show">
    Project ID: <input type="text" name="project_id" required><br>
    <input type="submit" value="show">
</form>
<table border="1">
    <tr>
        <th>Task_Id</th>
        <th>Title</th>
    </tr>
    <c:forEach var="task" items="${tasks}">
        <tr>
            <td><c:out value="${task.id}" /></td>
            <td><c:out value="${task.title}" /></td>

        </tr>
    </c:forEach>
</table>

<h2>Update</h2>
<h4>if you want to skip a line, add "0"</h4>
<form action="${pageContext.request.contextPath}/projects" method="post">
    <input type="hidden" name="action" value="update">
    ID: <input type="text" name="id" required><br>
    new Definition: <input type="text" name="definition" required><br>
    new Task ID: <input type="text" name="task1" required><br>
    new Task ID: <input type="text" name="task2" required><br>
    new Task ID: <input type="text" name="task3" required><br>
    new Task ID: <input type="text" name="task4" required><br>
    new Task ID: <input type="text" name="task5" required><br>
    <input type="submit" value="Update">
</form>

<h2>Delete Task</h2>
<form action="${pageContext.request.contextPath}/projects" method="post">
    <input type="hidden" name="action" value="delete">
    ID: <input type="text" name="id" required><br>
    <input type="submit" value="Delete">
</form>

<p style="text-align: center;">
    <a href="${pageContext.request.contextPath}/index.jsp">Back</a>
</p>

</body>
</html>
