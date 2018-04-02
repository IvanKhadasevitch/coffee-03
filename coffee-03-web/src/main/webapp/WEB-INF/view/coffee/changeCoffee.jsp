<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<fmt:setLocale value="${sessionScope.locale}"/>
<fmt:setBundle basename="messages" var="i18n"/>

<div class="col-sm-12 tableHeader"><i class="glyphicon glyphicon-thumbs-up"></i><fmt:message bundle="${i18n}" key="coffee.title"/></div>
<c:if test="${not empty requestScope.coffeeChangeErrorMsg}">
    <div class="col-sm-12 error"><fmt:message bundle="${i18n}" key="coffee.errorMsg"/></div>
</c:if>

<form class="form-horizontal" action="frontController?command=changeCoffee" method="post">
    <div class="form-group">
        <c:choose>
            <c:when test="${sessionScope.coffeeTypeForChange.getDisabled().equals(Character.valueOf('Y'))}">
                <label class="control-label col-sm-5" for="disabledChecked"><fmt:message bundle="${i18n}" key="coffee.disabled"/>? :</label>
                <div class="col-sm-4">
                    <input type="checkbox" id="disabledChecked"  name="disabled" checked />
                </div>
            </c:when>
            <c:otherwise>
                <label class="control-label col-sm-5" for="disabled"><fmt:message bundle="${i18n}" key="coffee.disabled"/>? :</label>
                <div class="col-sm-4">
                    <input type="checkbox" id="disabled" name="disabled" />
                </div>
            </c:otherwise>
        </c:choose>
    </div>
    <div class="form-group">
        <label class="control-label col-sm-5" for="typeName"><fmt:message bundle="${i18n}" key="coffee.name"/>:</label>
        <div class="col-sm-4">
            <input type="text" required maxlength="200" class="form-control" id="typeName" name="typeName"
                   placeholder="<fmt:message bundle="${i18n}" key="coffee.placeholder.name"/>"
                   value="${sessionScope.coffeeTypeForChange.typeName}">
        </div>
    </div>
    <div class="form-group">
        <label class="control-label col-sm-5" for="price"><fmt:message bundle="${i18n}" key="coffee.price"/>:</label>
        <div class="col-sm-4">
            <input type="number" required  class="form-control" id="price" name="price"
                   placeholder="<fmt:message bundle="${i18n}" key="coffee.placeholder.price"/>"
                   value="${sessionScope.coffeeTypeForChange.price}"
                   min="0" step="0.01">
        </div>
    </div>
    <div class="input-group col-sm-offset-4 col-sm-1">
        <button type="submit" class="btn btn-success"><fmt:message bundle="${i18n}" key="coffee.submit"/></button>
    </div>
</form>
