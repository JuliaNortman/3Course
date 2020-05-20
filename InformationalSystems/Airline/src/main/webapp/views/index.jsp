<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ include file="commons/header.jsp"%>
<div class="flights">
    <c:forEach items="${flights}" var="flight">
        <div class="flight" onclick="location.href='${pageContext.request.contextPath}/flights?flightId=${flight.id}'">
            <h2 style="text-align: center">Flight #${flight.id}</h2>
            <p>Direction:<span>${flight.direction}</span></p>
            <p>Departure date:<span>${flight.departureTime}</span></p>
            <p>Flight time:<span>${flight.flightTime}</span></p>
            <p>Number of seats available:<span>${flight.seatsNum}</span></p>
            <p>Price:<span>$${flight.price}</span></p>
        </div>
    </c:forEach>
</div>
<%@ include file="commons/footer.jsp"%>