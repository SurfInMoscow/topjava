<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jstl/fmt" %>

<html>
<head>
    <title>New Meal</title>
</head>
<body>
<h3><a href="index.html">Home</a></h3>
<section>
    <button onclick="window.history.back()">Назад</button>
<form method="post" action="meals" enctype="application/x-www-form-urlencoded">
    <hr>
    Дата:<br>
    <input type="datetime-local" name="dateTime" size=25>
    <br><br>
    Описание:<br>
    <input type="text" name="description" size="25">
    <br><br>
    Калории:<br>
    <input type="text" name="calories" size="10">
    <br><br>
    <button type="submit">Сохранить</button>
</form>
</section>
</body>
</html>
