package com.knu.ynortman.lab2.controller;

import static com.knu.ynortman.lab2.util.JsonConverter.makeJsonAnswer;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.knu.ynortman.lab2.exception.ServerException;
import com.knu.ynortman.lab2.model.City;
import com.knu.ynortman.lab2.service.CityService;
import com.knu.ynortman.lab2.service.CityServiceImpl;

import static com.knu.ynortman.lab2.util.JsonConverter.*;


@WebServlet
public class CityServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    private final Logger logger = LogManager.getRootLogger();
    private final CityService cityService;
    public CityServlet() {
        super();
        cityService = new CityServiceImpl();
    }
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String[] urls = request.getPathInfo().split("/");
		if(urls.length == 2 && urls[1].equals("all")) {
			List<City> cities;
			try {
				cities = cityService.getAllCities();
				if(cities.size() == 0) {
					response.sendError(404, "Resource not found");
				} else {
					makeJsonAnswer(cities, response);
				}
			} catch (ServerException e) {
				response.sendError(500);
			}
		} else {
			response.sendError(404, "Path not found");
			logger.error("Path not found");
		}
	}

}
