<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ include file="commons/header.jsp"%>

<table>
	<tr>
		<th>Direction</th>
		<th>Departure time</th>
		<th>Fill(%)</th>
		<th>Tickets Total Price($)</th>
		<th>Busy seats</th>
		<th>Popularuty</th>
		<th>Baggage($)</th>
		<th>Priority($)</th>

	</tr>

	<c:forEach items="${result}" var="res">
		<tr>
			<td>${res.direction}</td>
			<td>${res.departureTime}</td>
			<td>${res.busySeatsPercentage}</td>
			<td>${res.ticketTotalCost}$</td>
			<td>${res.busySeatsNum}</td>
			<td>${res.popularity}</td>
			<td>${res.baggageNum}(${res.baggageTotalCost}$)</td>
			<td>${res.priorityNum}(${res.priorityTotalCost}$)</td>
		</tr>

	</c:forEach>
</table>



<%@ include file="commons/footer.jsp"%>