<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ include file="commons/header.jsp"%>
<div class="cabinet">
    <div class="account">
        <p class="account-value">Balance: <span class="bold">$${user.account}</span></p>
        <button class="account-btn" onclick="location.href = '${pageContext.request.contextPath}/views/replenish.jsp'">Replenish</button>
    </div>
    <h3 class="welcm">Welcome, <span class="user-name">${user.login}</span></h3>
    <div style="display: flex">
        <div class="info-bl">
            <h3 class="bl-ttl">My info</h3>
            <div class="bl-info">
                <p>Username: <span class="bold">${user.login}</span></p>
                <p>Phone: <span class="bold">${user.phone}</span></p>
                <p>Address: <span class="bold">${user.address}</span></p>
                <p>Email: <span class="bold">${user.email}</span></p>
            </div>
        </div>
        <div class="info-bl">
            <h3 class="bl-ttl">My tickets</h3>
            <div class="bl-tickets">
                <c:forEach items="${user.tickets}" var="ticket">
                    <div class="sm-ticket">
                        <div class="ticket-cont sm-ticket-cont">
                            Flight <span class="bold">#${ticket.flight.id}</span>
                        </div>
                        <div class="ticket-cont sm-ticket-cont">
                            Direction: <span class="bold">${ticket.flight.direction}</span>
                        </div>
                        <div class="ticket-cont sm-ticket-cont">
                            Departure date: <span class="bold">${ticket.flight.departureTime}</span>
                        </div>
                        <div class="ticket-cont sm-ticket-cont">
                            Flight time: <span class="bold">${ticket.flight.flightTime}</span>
                        </div>
                        <div class="ticket-cont sm-ticket-cont">
                            Seat number: <span class="bold">${ticket.seat.number}</span>
                        </div>
                        <div class="ticket-cont sm-ticket-cont">
                            Baggage:
                            <c:if test="${ticket.baggage}">
                                <span class="bold">Available</span>
                            </c:if>
                            <c:if test="${!ticket.baggage}">
                                <span class="bold">Not available</span>
                            </c:if>
                        </div>
                        <div class="ticket-cont sm-ticket-cont">
                            Priority check-in and boarding:
                            <c:if test="${ticket.priority}">
                                <span class="bold">Available</span>
                            </c:if>
                            <c:if test="${!ticket.priority}">
                                <span class="bold">Not available</span>
                            </c:if>
                        </div>
                        <div class="line">

                        </div>
                        <div class="ticket-price sm-ticket-price">
                            Price: <span class="bold">$${ticket.price}</span>
                        </div>
                    </div>
                </c:forEach>
            </div>
        </div>
    </div>
</div>
<%@ include file="commons/footer.jsp"%>