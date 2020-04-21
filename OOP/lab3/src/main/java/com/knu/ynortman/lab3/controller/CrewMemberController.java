package com.knu.ynortman.lab3.controller;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(path = "crew",
				produces = "application/json")
@CrossOrigin(origins = "*")
public class CrewMemberController {

}
