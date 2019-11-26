<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jstl/fmt" %>
<%@taglib uri="http://pavel.vorobev.com" prefix="f" %>
<html>
<head>
    <link rel="stylesheet" href="css/style.css">
    <title>List of Meals with exceed</title>
    <style>
        .normal {
            color: green;
        }

        .excess {
            color: red;
        }
    </style>
</head>
<body>
<h3><a href="index.jsp">Home</a></h3>

<section>
    <form method="post" action="meals?action=filter">
        <dl>
            <dt>From Date:</dt>
            <dd><input type="date" name="startDate" value="${param.startDate}"></dd>
        </dl>
        <dl>
            <dt>To Date:</dt>
            <dd><input type="date" name="endDate" value="${param.endDate}"></dd>
        </dl>
        <dl>
            <dt>From Time:</dt>
            <dd><input type="time" name="startTime" value="${param.startTime}"></dd>
        </dl>
        <dl>
            <dt>To Time:</dt>
            <dd><input type="time" name="endTime" value="${param.endTime}"></dd>
        </dl>
        <button type="submit">Filter</button>
    </form>
    <table align=\"left\">
        <tr>
            <th>dateTime</th>
            <th>description</th>
            <th>calories</th>
            <th>excess</th>
            <th>Unique ID</th>
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
                    <a href="meals?id=${meals.id}&action=edit"><input type="button" value="Редактировать"></a>
                    <a href="meals?id=${meals.id}&action=delete"><input type="button" value="Удалить"></a>
                </td>
            </tr>
        </c:forEach>
    </table>
    <p><a href="meals?action=new"><input type="button" value="Добавить"></a></p>
</section>
</body>
</html>
