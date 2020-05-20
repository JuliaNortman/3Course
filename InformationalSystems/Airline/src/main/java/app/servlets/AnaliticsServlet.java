package app.servlets;

import java.io.IOException;
import java.sql.SQLException;
import java.text.ParseException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import app.entities.Result;
import app.services.AnaliticsService;

@WebServlet(urlPatterns = "/analitics")
public class AnaliticsServlet extends HttpServlet {
	private final AnaliticsService as;
	
	public AnaliticsServlet() throws ClassNotFoundException, SQLException, ParseException {
		as = new AnaliticsService();
	}
	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		try {
			List<Result> result = as.getAnalitics();
			System.out.println("Result " + result.size());
			request.setAttribute("result", result);
            request.getRequestDispatcher("/views/analitics.jsp").forward(request, response);
			//response.getWriter().write(as.getAnalitics());
		} catch (ClassNotFoundException | SQLException | ParseException e) {
			
			e.printStackTrace();
		} 
	}
}
