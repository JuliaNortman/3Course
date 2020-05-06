package com.knu.ynortman.lab2.controller;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet(
		urlPatterns = {
				"/flight/*",
				"/crew/*"
		})
public class DispatcherServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	
	private final String flightServletPath = "/flight";
	private final String crewMemberServletPath = "/crew";
    
	private FlightServlet flightController;
	private CrewMemberServlet memberController;

    public DispatcherServlet() {
        super();
        flightController = new FlightServlet();
        memberController = new CrewMemberServlet();
    }

	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String servletPath = request.getServletPath();
		if(servletPath.equals(flightServletPath)) {
			flightController.doGet(request, response);
		} else if(servletPath.equals(crewMemberServletPath)) {
			memberController.doGet(request, response);
		} else {
			//report error no such path
			response.sendError(404, "Path not found");
			response.getWriter().append("dispatcher error");
		}
	}

	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String servletPath = request.getServletPath();
		if(servletPath.equals(flightServletPath)) {
			flightController.doPost(request, response);
		} else if(servletPath.equals(crewMemberServletPath)) {
			memberController.doPost(request, response);
		} else {
			//report error no such path
			response.sendError(404, "Path not found");
		}
	}

}