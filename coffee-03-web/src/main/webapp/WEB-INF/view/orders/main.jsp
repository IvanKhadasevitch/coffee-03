<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<fmt:setLocale value="${sessionScope.locale}"/>
<fmt:setBundle basename="messages" var="i18n"/>

<c:if test="${not empty requestScope.orderErrorMsg}">
    <div class="error"><fmt:message bundle="${i18n}" key="orders.errorMsg"/></div>
</c:if>

<div class="container-fluid">
    <div class="col-md-12 tableHeader"><i class="glyphicon glyphicon-shopping-cart"></i><fmt:message bundle="${i18n}" key="orders.title"/></div>
    <c:if test="${not empty sessionScope.freeCup}">
        <div class="col-md-12 approve"><fmt:message bundle="${i18n}" key="orders.every"/> ${sessionScope.freeCup} <fmt:message bundle="${i18n}" key="orders.isFree"/></div>
    </c:if>

    <table class="table table-striped table-hover table-condensed">
        <tr>
            <th class="col-sm-8 gumHeader"><fmt:message bundle="${i18n}" key="orders.coffeeType"/></th>
            <th class="col-sm-2 gumHeader"><fmt:message bundle="${i18n}" key="orders.coffeePrice"/></th>
            <th class="col-sm-2 gumHeader"><fmt:message bundle="${i18n}" key="orders.quantity"/></th>
        </tr>
        <form class="form-horizontal" action="frontController?command=orders" method="post">
            <c:forEach var="coffeeType" items="${requestScope.coffeeTypeList}" varStatus="status">
                <tr class="info ">
                     <td class="col-sm-8 Left">${coffeeType.typeName}</td>
                     <td class="col-sm-2 Center"><fmt:formatNumber value="${coffeeType.price}" minFractionDigits="2" maxFractionDigits="2" type="number"/> TGR</td>
                     <td class="col-sm-2 Left">
                         <input type="number" class="form-control" name="orderQuantity${coffeeType.id}"
                                placeholder="<fmt:message bundle="${i18n}" key="orders.quantity"/>"
                                min="0"/>
                     </td>
                </tr>
            </c:forEach>
                <tr class="info">
                    <td class="col-sm-5"></td>
                    <td class="col-sm-5"></td>
                    <td class="col-sm-2 Right">
                        <button type="submit" class="btn btn-success"><fmt:message bundle="${i18n}" key="orders.submit"/></button>
                    </td>
                </tr>
        </form>
    </table>
</div>