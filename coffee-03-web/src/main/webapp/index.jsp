<?xml version="1.0" encoding="UTF-8"?>
<html xmlns:jsp="http://java.sun.com/JSP/Page"
      xmlns:c="http://java.sun.com/jsp/jstl/core"
      xmlns:fmt="http://java.sun.com/jsp/jstl/fmt"
      xmlns:u="urn:jsptagdir:/WEB-INF/tags/utils"
      xmlns:com="urn:jsptagdir:/WEB-INF/tags/common">

    <jsp:directive.page pageEncoding="UTF-8" contentType="text/html;charset UTF-8"/>

    <head>
        <title>Coffee</title>
    </head>

    <body>
        <c:out value="This is index.jsp: "/>
        <jsp:forward page="/frontController?command=orders"/>
    </body>
</html>