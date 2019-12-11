<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jstl/fmt" %>
<%@taglib uri="http://pavel.vorobev.com" prefix="f" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<html>
<head>
    <title>List of Meals with exceed</title>
</head>
<jsp:include page="fragments/headTag.jsp"/>
<body>
<jsp:include page="fragments/bodyHeader.jsp"/>
<section>
    <form method="get" action="meals/filter">
        <dl>
            <dt><spring:message code="meal.startDate"/></dt>
            <dd><input type="date" name="startDate" value="${param.startDate}"></dd>
        </dl>
        <dl>
            <dt><spring:message code="meal.endDate"/></dt>
            <dd><input type="date" name="endDate" value="${param.endDate}"></dd>
        </dl>
        <dl>
            <dt><spring:message code="meal.fromTime"/></dt>
            <dd><input type="time" name="startTime" value="${param.startTime}"></dd>
        </dl>
        <dl>
            <dt><spring:message code="meal.toTime"/></dt>
            <dd><input type="time" name="endTime" value="${param.endTime}"></dd>
        </dl>
        <button type="submit"><spring:message code="meal.filter"/></button>
    </form>
    <table align=\"left\">
        <tr>
            <th><spring:message code="meal.dateTime"/></th>
            <th><spring:message code="meal.description"/></th>
            <th><spring:message code="meal.calories"/></th>
            <th><spring:message code="meal.excess"/></th>
            <th><spring:message code="meal.uniqueID"/></th>
        </tr>
        <c:forEach var="meals" items="${meals}">
            <jsp:useBean id="meals" type="ru.javawebinar.topjava.to.MealTo"/>
            <tr class="${meals.excess ? 'excess' : 'normal'}">
                    <td>${f:formatLocalDateTime(meals.dateTime, "yyyy-MM-dd HH:mm")}</td>
                    <%--<td><%=meals.getDateTime().format((DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm")))%></td>--%>
                    <td>${meals.description}</td>
                    <td>${meals.calories}</td>
                    <td>${meals.excess}</td>
                <td>${meals.id}
                    <a href="meals/editMeal?id=${meals.id}"><input type="button" value="<spring:message code="common.edit"/>"></a>
                    <a href="meals/delete?id=${meals.id}"><input type="button" value="<spring:message code="common.delete"/>"></a>
                </td>
            </tr>
        </c:forEach>
    </table>
    <p><a href="meals/createMeal"><input type="button" value="<spring:message code="common.new"/>"></a></p>
</section>
</body>
</html>
