<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<fmt:setLocale value="${sessionScope.locale}"/>
<fmt:setBundle basename="messages" var="i18n"/>

<c:if test="${not empty requestScope.deliveryErrorMsg}">
    <div class="error"><fmt:message bundle="${i18n}" key="orders.delivery.errorMsg"/></div>
</c:if>

<div class="container-fluid">
    <div class="col-md-12 tableHeader"><i class="glyphicon glyphicon-shopping-cart"></i><fmt:message bundle="${i18n}" key="orders.title"/></div>

    <form class="form-horizontal" action="frontController?command=delivery" method="post">
        <div class="input-group col-sm-offset-4 col-sm-4">
            <span class="input-group-addon"><i class="glyphicon glyphicon-user"></i></span>
            <input type="text" class="form-control" name="customerName"  maxlength="100"
                   placeholder="<fmt:message bundle="${i18n}" key="orders.customerName"/>"
                   value="${requestScope.customerName}">
        </div>
        <div class="input-group col-sm-offset-4 col-sm-4">
            <span class="input-group-addon"><i class="glyphicon glyphicon-home"></i></span>
            <input type="text" required class="form-control" name="deliveryAddress"  maxlength="200"
                   placeholder="<fmt:message bundle="${i18n}" key="orders.deliveryAddress"/>"
                   value="${requestScope.deliveryAddress}">
        </div>
        <div class="input-group col-sm-offset-4 col-sm-1">
            <button type="submit" class="btn btn-success"><fmt:message bundle="${i18n}" key="orders.submit"/></button>
        </div>
    </form>

    <table class="table table-striped table-hover table-condensed">
        <tr>
            <th class="col-md-7 gumHeader"><fmt:message bundle="${i18n}" key="orders.coffeeType"/></th>
            <th class="col-md-2 gumHeader"><fmt:message bundle="${i18n}" key="orders.coffeePrice"/></th>
            <th class="col-md-1 gumHeader"><fmt:message bundle="${i18n}" key="orders.quantity"/></th>
            <th class="col-md-2 gumHeader"><fmt:message bundle="${i18n}" key="orders.itemTotal"/></th>
        </tr>
        <c:forEach var="coffeeOrderItemVo" items="${sessionScope.coffeeOrderItemVoList}" varStatus="status">
            <tr class="info gumRow">
                <td class="col-md-7 Left">${coffeeOrderItemVo.coffeeTypeName}</td>
                <td class="col-md-2 Right"><fmt:formatNumber value="${coffeeOrderItemVo.coffeePrice}" minFractionDigits="2" maxFractionDigits="2" type="number"/> TGR</td>
                <td class="col-md-1 Center">${coffeeOrderItemVo.quantity}</td>
                <td class="col-md-2 Right"><fmt:formatNumber value="${coffeeOrderItemVo.coffeePrice * coffeeOrderItemVo.quantity}" minFractionDigits="2" maxFractionDigits="2" type="number"/> TGR</td>
            </tr>
        </c:forEach>
    </table>
    <div style="text-align: center">
        <a href="frontController?command=orders" class="buttonRefer"><fmt:message bundle="${i18n}" key="orders.refer.otherOrder"/></a>
    </div>
</div>
