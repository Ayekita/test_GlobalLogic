package com.jcatalan.proyecto.cl.demo.dao;
import java.sql.*;

public class UserDao {
	
	public void testDatabase(String url) throws SQLException {
	    Connection connection= DriverManager.getConnection(url,"sa","");
	    PreparedStatement ps=connection.prepareStatement("SELECT UUID FROM USER WHERE EMAIL = 'prueba@lalala.com'");
	    ResultSet r=ps.executeQuery();
	    if(r.next()) {
	        System.out.println("data?");
	    }
	    r.close();
	    ps.close();
	    connection.close();
	}
}