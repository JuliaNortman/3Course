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
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String[] urls = request.getPathInfo().split("/");
		if(urls.length == 2) {
			if(urls[1].equals("all")) {
				List<CrewMember> members = memberService.getAllMembers();
				if(members == null || members.size() == 0) {
					response.sendError(404, "Resorce not found");
				} else {
					makeJsonAnswer(members, response);
				}
			} else {
				int id = Integer.parseInt(urls[1]);
				CrewMember member = memberService.getMember(id);
				if(member == null) {
					response.sendError(404, "Resorce not found");
				} else {
					makeJsonAnswer(member, response);
				}
			}
		} else if(urls.length == 3) {
			if(urls[1].equals("flight")) {
				int flightId = Integer.parseInt(urls[2]);
				List<CrewMember> crew = memberService.getAllFlightCrew(flightId);
				if(crew == null || crew.size() == 0) {
					response.sendError(404, "Resorce not found");
				} else {
					makeJsonAnswer(crew, response);
				}
			} else if(urls[2].equals("flights")) {
				int id = Integer.parseInt(urls[1]);
				List<Flight> flights = memberService.getAllFlightsForMember(id);
				if(flights.size() == 0) {
					response.sendError(404, "Resorce not found");
				} else {
					makeJsonAnswer(flights, response);
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
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
