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
import com.knu.ynortman.lab2.exception.ServerException;
import com.knu.ynortman.lab2.model.CrewMember;
import com.knu.ynortman.lab2.model.Flight;
import com.knu.ynortman.lab2.service.CrewMemberService;
import com.knu.ynortman.lab2.service.CrewMemberServiceImpl;

import static com.knu.ynortman.lab2.util.JsonConverter.*;

@WebServlet
public class CrewMemberServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	private final Logger logger = LogManager.getRootLogger();
	private final CrewMemberService memberService;

	public CrewMemberServlet() {
		super();
		this.memberService = new CrewMemberServiceImpl();
	}

	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String[] urls = request.getPathInfo().split("/");
		if (urls.length == 2) {
			if (urls[1].equals("all")) {
				List<CrewMember> members;
				try {
					members = memberService.getAllMembers();
					if (members == null || members.size() == 0) {
						response.sendError(404, "Resorce not found");
					} else {
						makeJsonAnswer(members, response);
					}
				} catch (ServerException e) {
					response.sendError(500);
				}
			} else {
				int id = Integer.parseInt(urls[1]);
				try {
					CrewMember member = memberService.getMember(id);
					if (member == null) {
						response.sendError(404, "Resorce not found");
					} else {
						makeJsonAnswer(member, response);
					}
				} catch (ServerException e) {
					response.sendError(500);
				}
			}
		} else if (urls.length == 3) {
			if (urls[1].equals("flight")) {
				int flightId = Integer.parseInt(urls[2]);
				List<CrewMember> crew;
				try {
					crew = memberService.getAllFlightCrew(flightId);
					if (crew == null || crew.size() == 0) {
						response.sendError(404, "Resorce not found");
					} else {
						makeJsonAnswer(crew, response);
					}
				} catch (ServerException e) {
					response.sendError(500);
				}
			} else if (urls[2].equals("flights")) {
				int id = Integer.parseInt(urls[1]);
				List<Flight> flights;
				try {
					flights = memberService.getAllFlightsForMember(id);
					if (flights.size() == 0) {
						response.sendError(404, "Resorce not found");
					} else {
						makeJsonAnswer(flights, response);
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
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String[] urls = request.getPathInfo().split("/");
		if (urls.length == 3 && urls[1].equals("dispatcher") && urls[2].equals("add")) {
			CrewMember member = new ObjectMapper().readValue(jsonBodyFromRequest(request, response), CrewMember.class);
			try {
				member = memberService.createMember(member);
				if (member == null) {
					response.sendError(400, "Bad request");
				} else {
					makeJsonAnswer(member, response);
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
	protected void doPut(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String[] urls = request.getPathInfo().split("/");
		if (urls.length == 3 && urls[1].equals("dispatcher") && urls[2].equals("update")) {
			CrewMember member = new ObjectMapper().readValue(jsonBodyFromRequest(request, response), CrewMember.class);
			try {
				member = memberService.update(member);
				if (member == null) {
					response.sendError(400, "Bad request");
				} else {
					makeJsonAnswer(member, response);
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
		if (urls.length == 4 && urls[1].equals("dispatcher") && urls[2].equals("delete")) {
			int id = Integer.parseInt(urls[3]);
			try {
				memberService.deleteById(id);
			} catch (ServerException e) {
				response.sendError(500);
			}
		} else {
			response.sendError(404, "Path not found");
			logger.error("Path " + request.getPathInfo() + " not found");
		}
	}

}
