package com.dream.database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConnectMSSQL implements ConnectDB{

	@Override
	public Connection connect() throws SQLException {
		String url = "jdbc:sqlserver://localhost";
		String user = "root";
		String pass = "bell2535";
		Connection conn = DriverManager.getConnection(url,user,pass);
		if(conn!=null){
			return conn;
		}else{
			return conn;
		}
	}

}
