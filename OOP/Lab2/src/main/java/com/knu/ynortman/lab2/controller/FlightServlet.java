package com.knu.ynortman.lab2.controller;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.knu.ynortman.lab2.model.Flight;
import com.knu.ynortman.lab2.service.FlightService;
import com.knu.ynortman.lab2.service.FlightServiceImpl;

@WebServlet
public class FlightServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private final Logger logger = LogManager.getRootLogger();
	
	private final String getPathAll = "all";
	private final FlightService flightService;
	
       
    public FlightServlet() {
        super();
        this.flightService = new FlightServiceImpl();
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String[] urls = request.getPathInfo().split("/");
		if(urls.length == 2) {
			if(urls[1].equals(getPathAll)) {
				response.getWriter().append("all");
				List<Flight> flights = flightService.getAllFlights();
				if(flights == null) {
					response.sendError(404, "Resource not found");
				} else {
					response.setContentType("application/json");
					response.setCharacterEncoding("UTF-8");
					PrintWriter out = response.getWriter();
					out.print(new ObjectMapper().writeValueAsString(flights));
					out.flush();
				}
			} else {
				try {
					int id = Integer.parseInt(urls[1]);
					response.getWriter().append(String.valueOf(id));
				} catch(NumberFormatException e) {
					response.sendError(404, "Path not found");
					logger.error("Path not found");
				}
			}
		} else {
			
		}
		//response.getWriter().append(request.getServletPath()).append(request.getPathInfo());
		
	}

	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
