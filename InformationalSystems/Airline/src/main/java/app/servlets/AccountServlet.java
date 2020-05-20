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

@WebServlet(name = "AccountServlet", urlPatterns = "/account")
public class AccountServlet extends HttpServlet {
    private UserService ps = new UserService();

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doGet(request, response);
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String op = request.getParameter("op");
        if (op.equals("login")){
            login(request, response);
        } else if (op.equals("logout")){
            logout(request, response);
        } else if (op.equals("registration")){
            registration(request, response);
        }
    }

    private void login(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException{
        try {
            String login = request.getParameter("username");
            String password = request.getParameter("password");
            User user = ps.getUser(login, password);

            if (user == null){
                response.getWriter().write("Login or password is incorrect");
                response.setHeader("Refresh","2;URL="+request.getContextPath()+"/views/login.jsp");
                return;
            }

            request.getSession().setAttribute("user",user);
            response.getWriter().write("Log in successful");
            response.setHeader("Refresh","1;URL="+request.getContextPath()+"/user?op=showCabinet");

        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    private void logout(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException{
        request.getSession().removeAttribute("user");
        response.getWriter().write("You logged out successfully ...");
        response.setHeader("Refresh", "1;URL="+request.getContextPath()+"/flights");
    }

    private void registration(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException{
        try {
            String login = request.getParameter("username");
            String password = request.getParameter("password");
            String phone = request.getParameter("phone");
            String address = request.getParameter("address");
            String email = request.getParameter("email");

            User user = new User(login, password, phone, address, email);

            ps.register(user);
            user = ps.getUser(login, password);

            request.getSession().setAttribute("user",user);
            response.getWriter().write("Registration successful");
            response.setHeader("Refresh","2;URL="+request.getContextPath()+"/user?op=showCabinet");

        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }
}
