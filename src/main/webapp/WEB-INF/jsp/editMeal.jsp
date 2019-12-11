<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jstl/fmt" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>

<html>
<head>
    <jsp:useBean id="meals" type="ru.javawebinar.topjava.model.Meal" scope="request"/>
    <title>Meal ${meals.id} - ${meals.description}</title>
</head>
<jsp:include page="fragments/headTag.jsp"/>
<body>
<jsp:include page="fragments/bodyHeader.jsp"/>
<br/>
<section>
    <button onclick="window.history.back()"><spring:message code="common.back"/></button>
    <hr>
    <form method="post" action="meals" enctype="application/x-www-form-urlencoded">
        <input type="hidden" name="id" value="${meals.id}">
        <spring:message code="common.date"/><br>
        <input type="datetime-local" name="dateTime" value="${meals.dateTime}" size=25>
        <br><br>
        <spring:message code="meal.description"/><br>
        <input type="text" name="description" value="${meals.description}" size="25">
        <br><br>
        <spring:message code="meal.calories"/><br>
        <input type="text" name="calories" value="${meals.calories}" size="10">
        <br><br>
        <button type="submit"><spring:message code="common.save"/></button>
    </form>
</section>
</body>
</html>
