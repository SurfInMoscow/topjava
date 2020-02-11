<%@ page contentType="text/html; charset=UTF-8" language="java" %>
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
<script type="text/javascript" src="resources/js/topjava.common.js" defer></script>
<script type="text/javascript" src="resources/js/topjava.meals.js" defer></script>
<jsp:include page="fragments/bodyHeader.jsp"/>
<div class="jumbotron pt-4" style="background-color: #dad9e1;">
    <form method="get" action="meals/filter">
        <dl>
            <dt><spring:message code="meal.startDate"/></dt>
            <dd><input type="date" id="startDate" name="startDate" value="${param.startDate}"></dd>
        </dl>
        <dl>
            <dt><spring:message code="meal.endDate"/></dt>
            <dd><input type="date" id="endDate" name="endDate" value="${param.endDate}"></dd>
        </dl>
        <dl>
            <dt><spring:message code="meal.fromTime"/></dt>
            <dd><input type="time" id="startTime" name="startTime" value="${param.startTime}"></dd>
        </dl>
        <dl>
            <dt><spring:message code="meal.toTime"/></dt>
            <dd><input type="time" id="endTime" name="endTime" value="${param.endTime}"></dd>
        </dl>
        <button type="submit"><spring:message code="meal.filter"/></button>
    </form>
    <div class="container" style="background-color: #dad9e1;">
        <button class="btn btn-primary" onclick="add()">
            <span class="fa fa-plus"></span>
            <spring:message code="meal.add"/>
        </button>
        <table class="table table-striped" id="datatable">
        <thead>
        <tr>
            <th><spring:message code="meal.dateTime"/></th>
            <th><spring:message code="meal.description"/></th>
            <th><spring:message code="meal.calories"/></th>
            <th><spring:message code="meal.excess"/></th>
            <th><spring:message code="meal.uniqueID"/></th>
            <th></th>
            <th></th>
        </tr>
        </thead>
        <c:forEach var="meals" items="${meals}">
            <jsp:useBean id="meals" scope="page" type="ru.javawebinar.topjava.to.MealTo"/>
            <tr>
                <td class="${meals.excess ? 'excess' : 'normal'}">${f:formatLocalDateTime(meals.dateTime, "yyyy-MM-dd HH:mm")}</td>
                <td class="${meals.excess ? 'excess' : 'normal'}">${meals.description}</td>
                <td class="${meals.excess ? 'excess' : 'normal'}">${meals.calories}</td>
                <td class="${meals.excess ? 'excess' : 'normal'}">${meals.excess}</td>
                <td class="${meals.excess ? 'excess' : 'normal'}">${meals.id}</td>
                <td><a href="meals/editMeal?id=${meals.id}"><span class="fa fa-pencil"></span></a></td>
                <td><a class="delete" id="${meals.id}"><span class="fa fa-remove"></span></a></td>
            </tr>
        </c:forEach>
        </table>
    </div>
</div>

<div class="modal fade" tabindex="-1" id="editRow">
    <div class="modal-dialog">
        <div class="modal-content">
            <div class="modal-header">
                <h4 class="modal-title"><spring:message code="meal.title"/></h4>
                <button type="button" class="close" data-dismiss="modal">&times;</button>
            </div>
            <div class="modal-body">
                <form id="detailsForm">
                    <div class="form-group">
                        <label for="dateTime" class="col-form-label"><spring:message code="meal.dateTime"/></label>
                        <input type="datetime-local" class="form-control" id="dateTime" name="dateTime"
                               placeholder="<spring:message code="meal.dateTime"/>">
                    </div>
                    <div class="form-group">
                        <label for="description" class="col-form-label"><spring:message code="meal.description"/></label>
                        <input type="text" class="form-control" id="description" name="description"
                               placeholder="<spring:message code="meal.description"/>">
                    </div>
                    <div class="form-group">
                        <label for="calories" class="col-form-label"><spring:message code="meal.calories"/></label>
                        <input type="text" class="form-control" id="calories" name="calories"
                               placeholder="<spring:message code="meal.calories"/>">
                    </div>
                </form>
            </div>
            <div class="modal-footer">
                <button type="button" class="btn btn-secondary" data-dismiss="modal">
                    <span class="fa fa-close"></span>
                    <spring:message code="common.cancel"/>
                </button>
                <button type="button" class="btn btn-primary" onclick="save()">
                    <span class="fa fa-check"></span>
                    <spring:message code="common.save"/>
                </button>
            </div>
        </div>
    </div>
</div>
<jsp:include page="fragments/footer.jsp"/>
</body>
</html>
