<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ include file="commons/header.jsp"%>
<div>
    <h1 class="cntr">Flight #${flight.id}</h1>
    <p class="cntr">Choose your seat:</p>
    <div class="seats">
        <c:forEach items="${flight.seats}" var="seat">
            <c:if test="${seat.free}">
                <div class="seat free" onclick="location.href='${pageContext.request.contextPath}/user/buy?op=buyTicketGUI&seatId=${seat.id}'">
                    <p class="number">${seat.number}</p>
                </div>
            </c:if>
            <c:if test="${!seat.free}">
                <div class="seat notfree">
                    <p class="number">${seat.number}</p>
                </div>
            </c:if>
        </c:forEach>
    </div>
    <p class="infos">Direction:<span class="infos-tip">${flight.direction}</span></p>
    <p class="infos">Departure date:<span class="infos-tip">${flight.departureTime}</span></p>
    <p class="infos">Flight time:<span class="infos-tip">${flight.flightTime}</span></p>
    <p class="infos">Price:<span class="infos-tip">$${flight.price}</span></p>
</div>
<%@ include file="commons/footer.jsp"%>