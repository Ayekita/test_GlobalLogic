package com.jcatalan.proyecto.cl.demo;

import static org.junit.jupiter.api.Assertions.*;

import java.sql.SQLException;

import org.junit.jupiter.api.Test;

import com.jcatalan.proyecto.cl.demo.dao.UserDao;

class PruebaConexion {

	@Test
	void test() {
		UserDao dao = new UserDao();
		try {
			dao.testDatabase("jdbc:h2:mem:bci");
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
