package com.knu.ynortman.lab2.dao;

import java.io.IOException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.LinkedList;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.knu.ynortman.lab2.exception.ServerException;
import com.knu.ynortman.lab2.model.CrewRole;
import com.knu.ynortman.lab2.model.CrewRoleEnum;
import com.knu.ynortman.lab2.util.JdbcConnection;

public class RoleDao {

	private final static Logger logger = LogManager.getRootLogger();
	private final static String getAllRolesQuery = "SELECT * FROM cregitw_role";
	
	public static List<CrewRole> getAllRoles() throws ServerException {
		List<CrewRole> roles = new LinkedList<>();
		try(Connection conn = JdbcConnection.getConnection()) {
			ResultSet rs = conn.prepareStatement(getAllRolesQuery).executeQuery();
			while(rs.next()) {
				CrewRole role = new CrewRole();
				role.setId(rs.getInt(1));
				role.setRole(CrewRoleEnum.valueOf(rs.getString(2)));
				roles.add(role);
			}
		} catch (SQLException | IOException e) {
			logger.error("Cannot get all roles");
			throw new ServerException();
		}
		return roles;
	}
}