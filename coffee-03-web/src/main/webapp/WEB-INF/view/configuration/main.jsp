<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<fmt:setLocale value="${sessionScope.locale}"/>
<fmt:setBundle basename="messages" var="i18n"/>

<div class="col-sm-12 tableHeader"><i class="glyphicon glyphicon-wrench"></i><fmt:message bundle="${i18n}" key="configuration.title"/></div>

<form class="form-horizontal" action="frontController?command=configuration" method="post">
    <div class="form-group">
        <label class="control-label col-sm-6" for="freeCup"><fmt:message bundle="${i18n}" key="configuration.freeCup"/>:</label>
        <div class="col-sm-4">
            <input type="number" class="form-control" id="freeCup" name="freeCup"
                   placeholder="<fmt:message bundle="${i18n}" key="configuration.placeholder.freeCup"/>"
                   value="${sessionScope.freeCup}"
                   min="1" step="1">
        </div>
    </div>
    <div class="form-group">
        <label class="control-label col-sm-6" for="freeDelivery"><fmt:message bundle="${i18n}" key="configuration.freeDelivery"/>:</label>
        <div class="col-sm-4">
            <input type="number" class="form-control" id="freeDelivery" name="freeDelivery"
                   placeholder="<fmt:message bundle="${i18n}" key="configuration.placeholder.freeDelivery"/>"
                   value="${sessionScope.freeDelivery}"
                   min="0" step="0.01">
        </div>
    </div>
    <div class="form-group">
        <label class="control-label col-sm-6" for="deliveryPrice"><fmt:message bundle="${i18n}" key="configuration.deliveryPrice"/>:</label>
        <div class="col-sm-4">
            <input type="number" class="form-control" id="deliveryPrice" name="deliveryPrice"
                   placeholder="<fmt:message bundle="${i18n}" key="configuration.placeholder.deliveryPrice"/>"
                   value="${sessionScope.deliveryPrice}"
                   min="0" step="0.01">
        </div>
    </div>
    <div class="input-group col-sm-offset-4 col-sm-1">
        <button type="submit" class="btn btn-success"><fmt:message bundle="${i18n}" key="configuration.submit"/></button>
    </div>
</form>
