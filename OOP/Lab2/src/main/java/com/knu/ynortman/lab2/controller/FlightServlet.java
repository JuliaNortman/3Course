package com.knu.ynortman.lab2.controller;

import java.io.BufferedReader;
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

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String[] urls = request.getPathInfo().split("/");
		if (urls.length == 2) {
			if (urls[1].equals(getPathAll)) {
				List<Flight> flights = flightService.getAllFlights();
				if (flights == null) {
					response.sendError(404, "Resource not found");
				} else {
					makeJsonAnswer(flights, response);
				}
			} else {
				try {
					int id = Integer.parseInt(urls[1]);
					Flight flight = flightService.getFlight(id);
					if (flight == null) {
						response.sendError(404, "Resource not found");
					} else {
						makeJsonAnswer(flight, response);
					}
				} catch (NumberFormatException e) {
					response.sendError(404, "Path not found");
					logger.error("Path not found");
				}
			}
		} else {
			response.sendError(404, "Path not found");
			logger.error("Path not found");
		}
		// response.getWriter().append(request.getServletPath()).append(request.getPathInfo());

	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String[] urls = request.getPathInfo().split("/");
		if (urls.length == 3 && urls[1].equals("admin") && urls[2].equals("add")) {
			// admin/add
			StringBuffer jsonBody = new StringBuffer();
			String line = null;
			try {
				BufferedReader reader = request.getReader();
				while ((line = reader.readLine()) != null) {
					jsonBody.append(line);
				}
			} catch (Exception e) { 
				logger.error("Cannot get json flight");
				response.sendError(400, "Bad request");
			}
			Flight flight = new ObjectMapper().readValue(jsonBody.toString(), Flight.class);
			flight = flightService.createFlight(flight);
			if(flight == null) {
				response.sendError(400, "Bad request");
			} else {
				response.setStatus(201);
				makeJsonAnswer(flight, response);
			}
		} else if (urls.length == 4) {
			// dispatcher/{id}/addmember

		}
	}

	private <T> void makeJsonAnswer(T obj, HttpServletResponse response) throws IOException {
		response.setContentType("application/json");
		response.setCharacterEncoding("UTF-8");
		PrintWriter out = response.getWriter();
		out.print(new ObjectMapper().writeValueAsString(obj));
		out.flush();
	}

}
