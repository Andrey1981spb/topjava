<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="fn" uri="http://topjava.javawebinar.ru/functions" %>
<html>
<jsp:include page="fragments/headTag.jsp"/>
<body>
<script type="text/javascript" src="resources/js/topjava.common.js" defer></script>
<script type="text/javascript" src="resources/js/topjava.meal.js" defer></script>
<jsp:include page="fragments/bodyHeader.jsp"/>

<section>
    <h3><spring:message code="meal.title"/></h3>

    <form id="detailsForm">
        <div class="form-group">
            <label for="startDate" class="col-form-label"><spring:message code="meal.startDate"/></label>
            <input type="date" class="form-control" id="startDate" name="startDate"
                   placeholder="<spring:message code="meal.startDate"/>">
        </div>

        <div class="form-group">
            <label for="endDate" class="col-form-label"><spring:message code="meal.endDate"/></label>
            <input type="date" class="form-control" id="endDate" name="endDate"
                   placeholder="<spring:message code="meal.endDate"/>">
        </div>

        <div class="form-group">
            <label for="startTime" class="col-form-label"><spring:message code="meal.startTime"/></label>
            <input type="time" class="form-control" id="startTime" name="startTime"
                   placeholder="<spring:message code="meal.startDate"/>">
        </div>

        <div class="form-group">
            <label for="endTime" class="col-form-label"><spring:message code="meal.endTime"/></label>
            <input type="time" class="form-control" id="endTime" name="endTime"
                   placeholder="<spring:message code="meal.endTime"/>">
        </div>

        <button type="button" class="btn btn-primary" onclick="getBetween()">
            <span class="fa fa-check"></span>
            <spring:message code="meal.filter"/>
        </button>
    </form>


    <div class="jumbotron pt-4">
        <div class="container">
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
                    <th></th>
                    <th></th>
                </tr>
                </thead>
                <c:forEach items="${meals}" var="mealTo">
                    <jsp:useBean id="mealTo" type="ru.javawebinar.topjava.to.MealTo"/>
                    <tr data-mealExcess="${mealTo.excess}">
                        <td>
                                <%--${meal.dateTime.toLocalDate()} ${meal.dateTime.toLocalTime()}--%>
                                <%--<%=TimeUtil.toString(meal.getDateTime())%>--%>
                                <%--${fn:replace(meal.dateTime, 'T', ' ')}--%>
                                ${fn:formatDateTime(mealTo.dateTime)}
                        </td>
                        <td>${mealTo.description}</td>
                        <td>${mealTo.calories}</td>
                        <td><a><span class="fa fa-pencil"></span></a></td>
                        <td><a class="delete" id="${mealTo.id}"><span class="fa fa-remove"></span></a></td>
                    </tr>
                </c:forEach>
            </table>
        </div>
    </div>

</section>
<jsp:include page="fragments/footer.jsp"/>
</body>
</html>