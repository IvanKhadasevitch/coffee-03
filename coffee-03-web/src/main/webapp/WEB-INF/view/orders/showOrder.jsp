<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<fmt:setLocale value="${sessionScope.locale}"/>
<fmt:setBundle basename="messages" var="i18n"/>

<div class="col-md-12 tableHeader"><i class="glyphicon glyphicon-shopping-cart"></i><fmt:message bundle="${i18n}" key="orders.title"/></div>
<c:if test="${not empty sessionScope.freeCup}">
    <div class="col-md-12 approve"><fmt:message bundle="${i18n}" key="orders.every"/> ${sessionScope.freeCup} <fmt:message bundle="${i18n}" key="orders.isFree"/></div>
</c:if>
<table class="table table-striped table-hover table-condensed">
    <tr>
        <td class="col-sm-4 Left"><strong><fmt:message bundle="${i18n}" key="orders.customerName"/>: </strong></td>
        <td class="col-sm-3 Left"><strong>${sessionScope.coffeeOrderAndCost.coffeeOrder.customerName}</strong></td>
        <td class="col-sm-3 Center"><fmt:formatDate value="${sessionScope.coffeeOrderAndCost.coffeeOrder.orderDate}" type="both" /></td>
        <td class="col-sm-2"></td>
    </tr>
    <tr>
        <td class="col-sm-4 Left"><strong><fmt:message bundle="${i18n}" key="orders.deliveryAddress"/>: </strong></td>
        <td class="col-sm-3 Left"><strong>${sessionScope.coffeeOrderAndCost.coffeeOrder.deliveryAddress}</strong></td>
        <td class="col-sm-3"></td>
        <td class="col-sm-2"></td>
    </tr>
    <tr>
        <th class="col-sm-4 gumHeader"><fmt:message bundle="${i18n}" key="orders.coffeeType"/></th>
        <th class="col-sm-3 gumHeader"><fmt:message bundle="${i18n}" key="orders.coffeePrice"/></th>
        <th class="col-sm-3 gumHeader"><fmt:message bundle="${i18n}" key="orders.quantity"/></th>
        <th class="col-sm-2 gumHeader"><fmt:message bundle="${i18n}" key="orders.itemTotal"/></th>
    </tr>
    <c:forEach var="coffeeOrderItemVo" items="${sessionScope.coffeeOrderItemVoList}" varStatus="status">
        <tr class="info gumRow">
            <td class="col-sm-4 Left">${coffeeOrderItemVo.coffeeTypeName}</td>
            <td class="col-sm-3 Right"><fmt:formatNumber value="${coffeeOrderItemVo.coffeePrice}" minFractionDigits="2" maxFractionDigits="2" type="number"/> TGR</td>
            <td class="col-sm-3 Center">${coffeeOrderItemVo.quantity}</td>
            <td class="col-sm-2 Right"><fmt:formatNumber value="${coffeeOrderItemVo.coffeePrice * coffeeOrderItemVo.quantity}" minFractionDigits="2" maxFractionDigits="2" type="number"/> TGR</td>
        </tr>
    </c:forEach>
    <tr>
        <td class="col-sm-4"></td>
        <td class="col-sm-3"></td>
        <td class="col-sm-3 Right"><fmt:message bundle="${i18n}" key="orders.totalForCoffee"/>: </td>
        <td class="col-sm-2 Left"><fmt:formatNumber value="${sessionScope.coffeeOrderAndCost.cost.coffeeTotalCost}" minFractionDigits="2" maxFractionDigits="2" type="number"/> TGR</td>
    </tr>
    <tr>
        <td class="col-sm-4"></td>
        <td class="col-sm-3"></td>
        <td class="col-sm-3 Right"><fmt:message bundle="${i18n}" key="orders.totalForDelivery"/>: </td>
        <td class="col-sm-2 Left"><fmt:formatNumber value="${sessionScope.coffeeOrderAndCost.cost.deliveryCost}" minFractionDigits="2" maxFractionDigits="2" type="number"/> TGR</td>
    </tr>
    <tr>
        <td class="col-sm-4"></td>
        <td class="col-sm-3"></td>
        <td class="col-sm-3 Right"><strong><fmt:message bundle="${i18n}" key="orders.total"/>: </strong></td>
        <td class="col-sm-2 Left"><strong><fmt:formatNumber value="${sessionScope.coffeeOrderAndCost.cost.coffeeTotalCost + sessionScope.coffeeOrderAndCost.cost.deliveryCost}" minFractionDigits="2" maxFractionDigits="2" type="number"/> TGR</strong></td>
    </tr>
</table>

<div style="text-align: center">
    <a href="frontController?command=orders" class="buttonRefer"><fmt:message bundle="${i18n}" key="orders.refer.otherOrder"/></a>
</div>
