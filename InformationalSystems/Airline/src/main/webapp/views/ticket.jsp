<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ include file="commons/header.jsp"%>
<div class="ticket-big" style="position: relative; width: 50%; left: 25%; border: 2px solid darkblue; margin-top: 40px">
    <h1 class="cntr">Ticket</h1>
    <form action="${pageContext.request.contextPath}/user/buy?op=buyTicket" method="post">
        <input type="text" name="seatId" value="${seat.id}" style="display: none">
        <input type="text" name="userId" value="${sessionScope.user.id}" style="display: none">
        <div class="ticket-cont">
            Flight #
            <input type="text" value="${flight.id}" class="ticket-inp" readonly style="width: 85%"/>
        </div>
        <div class="ticket-cont">
            Direction:
            <input type="text" value="${flight.direction}" class="ticket-inp" readonly style="width: 81%"/>
        </div>
        <div class="ticket-cont">
            Departure date:
            <input type="text" value="${flight.departureTime}" class="ticket-inp" readonly style="width: 71.5%"/>
        </div>
        <div class="ticket-cont">
            Flight time:
            <input type="text" value="${flight.flightTime}" class="ticket-inp" readonly style="width: 78%"/>
        </div>
        <div class="ticket-cont">
            Seat number:
            <input type="text" value="${seat.number}" class="ticket-inp" readonly style="width: 75.5%"/>
        </div>
        <div class="ticket-cont" style="padding-bottom: 5px; margin-top: 13px">
            Baggage (+$25):
            <select class="ticket-select" style="width: 71%" id="baggage" name="baggage" onchange="addBaggage()">
                <option>Available</option>
                <option selected>Not available</option>
            </select>
        </div>
        <div class="ticket-cont" style="padding-bottom: 5px; margin-top: 13px">
            Priority check-in and boarding (+$18):
            <select class="ticket-select" style="width: 35%" id="priority" name="priority" onchange="addPriority()">
                <option>Available</option>
                <option selected>Not available</option>
            </select>
        </div>
        <div class="line">

        </div>
        <div class="ticket-price">
            Total price: $
            <input type="text" name="price" id="price" value="${flight.price}" class="ticket-price-inp" readonly/>
        </div>
        <div class="line">

        </div>
        <input type="submit" value="Buy ticket" class="buy-btn">
    </form>
</div>
<script>
    function addBaggage(){
        var baggage = document.getElementById("baggage");
        console.log(baggage.value);
        var price = document.getElementById("price");
        var val = parseFloat(price.value);
        console.log(val);
        if (baggage.value === "Available"){
            val += 25;
            price.value = val;
        } else if (baggage.value === "Not available"){
            val -= 25;
            price.value = val;
        }
    }

    function addPriority(){
        var priority = document.getElementById("priority");
        var price = document.getElementById("price");
        var val = parseFloat(price.value);
        if (priority.value === "Available"){
            val += 18;
            price.value = val;
        } else if (priority.value === "Not available"){
            val -= 18;
            price.value = val;
        }
    }
</script>
<%@ include file="commons/footer.jsp"%>