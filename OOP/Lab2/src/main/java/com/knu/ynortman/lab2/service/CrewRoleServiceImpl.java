package com.knu.ynortman.lab2.service;

import java.util.List;

import com.knu.ynortman.lab2.dao.RoleDao;
import com.knu.ynortman.lab2.exception.ServerException;
import com.knu.ynortman.lab2.model.CrewRole;

public class CrewRoleServiceImpl implements CrewRoleService {

	@Override
	public List<CrewRole> getAllRoles() throws ServerException {
		return RoleDao.getAllRoles();
	}

}