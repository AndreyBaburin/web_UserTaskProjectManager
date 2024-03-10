<html>
<head>
    <title>CRUD Example</title>
</head>
<body>
<h2>Welcome to User-Task-Project Manager</h2>

<h2>Choose action</h2>
<div class="link-container">
    <a href="${pageContext.request.contextPath}/users?action=showAll">View All Users</a>
</div>
<div class="link-container">
    <a href="${pageContext.request.contextPath}/tasks?action=tasksPage">View All Tasks</a>
</div>
<div class="link-container">
    <a href="${pageContext.request.contextPath}/projects?action=projectPage">View All Projects</a>
</div>


</body>
</html>
