<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<fmt:setLocale value="${sessionScope.locale}"/>
<fmt:setBundle basename="messages" var="i18n"/>

<div class="col-sm-12 tableHeader"><i class="glyphicon glyphicon-thumbs-up"></i><fmt:message bundle="${i18n}" key="coffee.title"/></div>
<c:if test="${not empty requestScope.coffeeErrorMsg}">
    <div class="col-sm-12 error"><fmt:message bundle="${i18n}" key="coffee.errorMsg"/></div>
</c:if>

<div style="text-align: center">
    <a href="frontController?command=addCoffee" class="buttonRefer"><fmt:message bundle="${i18n}" key="coffee.refer.typeAdd"/></a>
</div>

<table class="table table-striped table-hover table-condensed">
    <tr>
        <th class="col-sm-1 gumHeader"><fmt:message bundle="${i18n}" key="coffee.disabled"/></th>
        <th class="col-sm-8 gumHeader"><fmt:message bundle="${i18n}" key="coffee.name"/></th>
        <th class="col-sm-2 gumHeader"><fmt:message bundle="${i18n}" key="coffee.price"/></th>
        <th class="col-sm-1 gumHeader"><fmt:message bundle="${i18n}" key="coffee.change"/></th>
    </tr>
    <form class="form-horizontal" action="frontController?command=coffee" method="post">
        <c:forEach var="coffeeType" items="${requestScope.coffeeTypeList}" varStatus="status">
            <tr class="info">
                <c:choose>
                    <c:when test="${coffeeType.getDisabled().equals(Character.valueOf('Y'))}">
                        <td class="col-sm-1 Center">
                            <input type="checkbox" name="disabled${coffeeType.id}" checked />
                        </td>
                    </c:when>
                    <c:otherwise>
                        <td class="col-sm-1 Center">
                            <input type="checkbox" name="disabled${coffeeType.id}" />
                        </td>
                    </c:otherwise>
                </c:choose>
                <td class="col-sm-8 Left">${coffeeType.typeName}</td>
                <td class="col-sm-2 Center"><fmt:formatNumber value="${coffeeType.price}" minFractionDigits="2" maxFractionDigits="2" type="number"/> TGR</td>
                <td class="col-sm-1 Center">
                    <input type="submit" name="coffeeTypeId${coffeeType.id}" title="<fmt:message bundle="${i18n}" key="coffee.change"/>" value="${coffeeType.id}" />
                </td>
            </tr>
        </c:forEach>

        <tr class="info">
            <td class="col-sm-1"></td>
            <td class="col-sm-8"></td>
            <td class="col-sm-2"></td>
            <td class="col-sm-1 Right">
                <button type="submit" class="btn btn-success"><fmt:message bundle="${i18n}" key="coffee.apply"/></button>
            </td>
        </tr>
    </form>
</table>