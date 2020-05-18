package com.knu.ynortman.lab2.controller;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.knu.ynortman.lab2.dao.FlightDao;
import com.knu.ynortman.lab2.exception.ServerException;
import com.knu.ynortman.lab2.model.CrewMember;
import com.knu.ynortman.lab2.model.Flight;
import com.knu.ynortman.lab2.service.FlightService;
import com.knu.ynortman.lab2.service.FlightServiceImpl;

import static com.knu.ynortman.lab2.util.JsonConverter.*;

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
			if (urls[1].equals(getPathAll)) { // flight/all
				List<Flight> flights;
				try {
					flights = flightService.getAllFlights();
					if (flights == null || flights.size() == 0) {
						response.sendError(404, "Resource not found");
					} else {
						makeJsonAnswer(flights, response);
					}
				} catch (ServerException e) {
					response.sendError(500);
				}
			} else { //flight/5
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
				} catch (ServerException e) {
					response.sendError(500);
				}
			}
		} else {
			response.sendError(404, "Path not found");
			logger.error("Path not found");
		}

	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String[] urls = request.getPathInfo().split("/");
		if (urls.length == 3 && urls[1].equals("admin") && urls[2].equals("add")) {
			// admin/add
			Flight flight = new ObjectMapper().readValue(jsonBodyFromRequest(request, response), Flight.class);
			try {
				flight = flightService.createFlight(flight);
				if (flight == null) {
					response.sendError(400, "Bad request");
				} else {
					response.setStatus(201);
					makeJsonAnswer(flight, response);
				}
			} catch (ServerException e) {
				response.sendError(500);
			}
		} else if (urls.length == 4) {
			if (urls[1].equals("dispatcher") && urls[3].equals("addmember")) {
				int flightId = Integer.parseInt(urls[2]);
				CrewMember member = new ObjectMapper().readValue(jsonBodyFromRequest(request, response),
						CrewMember.class);
				Flight flight;
				try {
					flight = flightService.addMember(flightId, member);
					if (flight == null) {
						response.sendError(400, "Bad request");
					} else {
						response.setStatus(201);
						makeJsonAnswer(flight, response);
					}
				} catch (ServerException e) {
					response.sendError(500);
				}
			} else {
				response.sendError(404, "Path not found");
				logger.error("Path not found");
			}
		} else {
			response.sendError(404, "Path not found");
			logger.error("Path not found");
		}
	}

	@Override
	protected void doPut(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String[] urls = request.getPathInfo().split("/");
		if(urls.length == 3 && urls[1].equals("admin") && urls[2].equals("update")) {
			Flight flight = new ObjectMapper().readValue(jsonBodyFromRequest(request, response),
					Flight.class);
			try {
				flight = flightService.update(flight);
				if(flight == null) {
					response.sendError(400, "Bad request");
				} else {
					makeJsonAnswer(flight, response);
				}
			} catch (ServerException e) {
				response.sendError(500);
			}
		} else {
			response.sendError(404, "Path not found");
			logger.error("Path not found");
		}
	}
	
	@Override
	protected void doDelete(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String[] urls = request.getPathInfo().split("/");
		if(urls.length == 4) {
			if(urls[1].equals("admin") && urls[2].equals("delete")) {
				int id = Integer.parseInt(urls[3]);
				try {
					flightService.deleteById(id);
				} catch (ServerException e) {
					response.sendError(500);
				}
			} else if(urls[1].equals("dispatcher") && urls[3].equals("deletemember")) {
				int flightId = Integer.parseInt(urls[2]);
				CrewMember member = new ObjectMapper().readValue(jsonBodyFromRequest(request, response),
						CrewMember.class);
				Flight flight;
				try {
					flight = FlightDao.deleteFlightMember(flightId, member);
					if(flight == null) {
						response.sendError(400, "Bad request");
					} else {
						makeJsonAnswer(flight, response);
					}
				} catch (ServerException e) {
					response.sendError(500);
				}
			} else {
				response.sendError(404, "Path not found");
				logger.error("Path not found");
			}
		} else {
			response.sendError(404, "Path not found");
			logger.error("Path not found");
		}
	}

	
}