<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jstl/fmt" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>

<html>
<head>
    <title>New Meal</title>
</head>
<jsp:include page="fragments/headTag.jsp"/>
<body>
<jsp:include page="fragments/bodyHeader.jsp"/>
<br/>
<section>
    <button onclick="window.history.back()">Назад</button>
<form method="post" action="meals" enctype="application/x-www-form-urlencoded">
    <hr>
    <spring:message code="common.date"/><br>
    <input type="datetime-local" name="dateTime" size=25>
    <br><br>
    <spring:message code="meal.description"/><br>
    <input type="text" name="description" size="25">
    <br><br>
    <spring:message code="meal.calories"/><br>
    <input type="text" name="calories" size="10">
    <br><br>
    <button type="submit"><spring:message code="common.save"/></button>
</form>
</section>
</body>
</html>
