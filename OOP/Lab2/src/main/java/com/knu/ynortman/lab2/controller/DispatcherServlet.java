package com.knu.ynortman.lab2.controller;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

@WebServlet(
		urlPatterns = {
				"/flight/*",
				"/crew/*",
				"/role/*",
				"/city/*"
		})
public class DispatcherServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private final Logger logger = LogManager.getRootLogger();
	
	private final String flightServletPath = "/flight";
	private final String crewMemberServletPath = "/crew";
	private final String crewRoleServletPath = "/role";
	private final String cityServletPath = "/city";
    
	private FlightServlet flightController;
	private CrewMemberServlet memberController;
	private CrewRoleServlet roleController;
	private CityServlet cityController;

    public DispatcherServlet() {
        super();
        flightController = new FlightServlet();
        memberController = new CrewMemberServlet();
        roleController = new CrewRoleServlet();
        cityController =  new CityServlet();
    }

	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String servletPath = request.getServletPath();
		if(servletPath.equals(flightServletPath)) {
			flightController.doGet(request, response);
		} else if(servletPath.equals(crewMemberServletPath)) {
			memberController.doGet(request, response);
		} else if(servletPath.equals(crewRoleServletPath)) {
			roleController.doGet(request, response);
		} else if(servletPath.equals(cityServletPath)) {
			cityController.doGet(request, response);
		} else {
			response.sendError(404, "Path not found");
			logger.error("dispatcher error");
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
	
	@Override
	protected void doPut(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String servletPath = request.getServletPath();
		if(servletPath.equals(flightServletPath)) {
			flightController.doPut(request, response);
		} else if(servletPath.equals(crewMemberServletPath)) {
			memberController.doPut(request, response);
		} else {
			//report error no such path
			response.sendError(404, "Path not found");
		}
	}
	
	@Override
	protected void doDelete(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String servletPath = request.getServletPath();
		if(servletPath.equals(flightServletPath)) {
			flightController.doDelete(request, response);
		} else if(servletPath.equals(crewMemberServletPath)) {
			memberController.doDelete(request, response);
		} else {
			//report error no such path
			response.sendError(404, "Path not found");
		}
	}

}