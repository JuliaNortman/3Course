package app.servlets;

import app.entities.Flight;
import app.entities.Seat;
import app.services.FlightService;
import app.services.TicketService;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;
import java.text.ParseException;

@WebServlet(name = "TicketServlet", urlPatterns = "/user/buy")
public class TicketServlet extends HttpServlet {
    private FlightService fs = new FlightService();
    private TicketService ts = new TicketService();

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doGet(request, response);
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String op = request.getParameter("op");
        if (op.equals("buyTicketGUI")){
            buyTicketGUI(request, response);
        } else if (op.equals("buyTicket")){
            buyTicket(request, response);
        }

    }

    private void buyTicketGUI(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {
            String seatIdStr = request.getParameter("seatId");
            int seatId = Integer.parseInt(seatIdStr);
            Seat seat = fs.getSeat(seatId);
            Flight flight = fs.getFlight(seat.getFlightId());
            if (flight == null){
                request.setAttribute("message", "Error occurred");
                request.getRequestDispatcher("/views/message.jsp").forward(request, response);
            }else {
                request.setAttribute("flight", flight);
                request.setAttribute("seat", seat);
                request.getRequestDispatcher("/views/ticket.jsp").forward(request, response);
            }
        } catch (SQLException | ClassNotFoundException | ParseException e) {
            e.printStackTrace();
        }
    }

    private void buyTicket(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {
            String seatId = request.getParameter("seatId");
            String userId = request.getParameter("userId");
            String baggage = request.getParameter("baggage");
            String priority = request.getParameter("priority");
            String price = request.getParameter("price");

            boolean isBought = ts.buyTicket(seatId, userId, baggage, priority, price);

            if (isBought){
                response.getWriter().write("Ticket successfully bought");
                response.setHeader("Refresh","2;URL="+request.getContextPath()+"/user?op=showCabinet");
            } else {
                request.setAttribute("message", "Not enough money on account. Click <a href='" + request.getContextPath() + "/views/replenish.jsp'>here</a> to replenish");
                request.getRequestDispatcher("/views/message.jsp").forward(request, response);
            }


        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }
}
