package com.knu.ynortman.lab2.service;

import java.util.List;

import com.knu.ynortman.lab2.exception.ServerException;
import com.knu.ynortman.lab2.model.CrewRole;

public interface CrewRoleService {
	List<CrewRole> getAllRoles() throws ServerException;
}
