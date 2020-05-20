package app.servlets;

import app.entities.User;
import app.services.UserService;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;

@WebServlet(name = "UserServlet", urlPatterns = "/user")
public class UserServlet extends HttpServlet {
    private UserService us = new UserService();

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doGet(request, response);
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String op = request.getParameter("op");
        if (op.equals("showCabinet")){
            showCabinet(request, response);
        } else if (op.equals("replenishAccount")){
            replenishAccount(request, response);
        }

    }

    private void showCabinet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException{
        try {
            User sessionUser = (User) request.getSession().getAttribute("user");
            if(sessionUser==null){
                response.sendRedirect(request.getContextPath()+"/views/login.jsp");
                return;
            }
            
            System.out.println("Session user " + sessionUser.getId());
            
            User user = us.getUserById(sessionUser.getId());

            request.getSession().setAttribute("user",user);
            request.getRequestDispatcher("/views/user.jsp").forward(request, response);

        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    private void replenishAccount(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException{
        try {
        	System.out.println("Replanish");
            String money = request.getParameter("money");
            User user = (User) request.getSession().getAttribute("user");
            us.replenish(user.getId(), money);
            response.getWriter().write("Successfully replenished");
            response.setHeader("Refresh","0;URL="+request.getContextPath()+"/user?op=showCabinet");
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }
}
