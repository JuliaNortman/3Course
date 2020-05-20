<%--
  Created by IntelliJ IDEA.
  User: Alexander
  Date: 16.04.2020
  Time: 19:47
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
  <head>
    <title>$Title$</title>
  </head>
  <body>
  <jsp:forward  page="/flights" >
    <jsp:param value="listPaintings" name="op" />
  </jsp:forward>
  </body>
</html>
