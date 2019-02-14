<%@ page import="java.time.format.DateTimeFormatter" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jstl/fmt" %>
<%@taglib uri="http://pavel.vorobev.com" prefix="f" %>
<html>
<head>
    <link rel="stylesheet" href="css/style.css">
    <title>List of Meals with exceed</title>
</head>
<body>
<h3><a href="index.html">Home</a></h3>

<section>
    <table align=\"left\">
        <tr>
            <th>dateTime</th>
            <th>description</th>
            <th>calories</th>
            <th>excess</th>
            <th>Unique ID</th>
        </tr>
        <c:forEach var="meals" items="${meals}">
            <tr>
                <jsp:useBean id="meals" type="ru.javawebinar.topjava.model.MealTo"/>
                <c:if test="${meals.excess == true}">
                    <td style="color: crimson">${f:formatLocalDateTime(meals.dateTime, "yyyy-MM-dd HH:mm")}</td>
                    <%--<td><%=meals.getDateTime().format((DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm")))%></td>--%>
                    <td style="color: crimson">${meals.description}</td>
                    <td style="color: crimson">${meals.calories}</td>
                    <td style="color: crimson">${meals.excess}</td>
                </c:if>
                <c:if test="${meals.excess == false}">
                    <td style="color: forestgreen">${f:formatLocalDateTime(meals.dateTime, "yyyy-MM-dd HH:mm")}</td>
                    <td style="color: forestgreen">${meals.description}</td>
                    <td style="color: forestgreen">${meals.calories}</td>
                    <td style="color: forestgreen">${meals.excess}</td>
                </c:if>
                <td>${meals.id} <a href="meals?id=${meals.id}&action=delete"><input type="button" value="Удалить"></a></td>
            </tr>
        </c:forEach>
    </table>
</section>

</body>
</html>
