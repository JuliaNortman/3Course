package com.knu.ynortman.lab2.controller;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet
public class FlightServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	
	private final String getPathAll = "all";
	
       
    public FlightServlet() {
        super();
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String[] urls = request.getPathInfo().split("/");
		if(urls.length == 2) {
			if(urls[1].equals(getPathAll)) {
				response.getWriter().append("all");
			} else {
				try {
					int id = Integer.parseInt(urls[1]);
					response.getWriter().append(String.valueOf(id));
				} catch(NumberFormatException e) {
					//report error no such path
					response.sendError(404, "Path not found");
					response.getWriter().append("flight error");
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
