package com.dream.database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class JDBCDriver implements ConnectDB {

	public JDBCDriver() {

	}

	@Override
	public Connection connect() throws SQLException {
		try {
			Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		String url = "jdbc:sqlserver://rnhyrbrn6o.database.windows.net:1433;database=dream_db;user=dreamService@rnhyrbrn6o;password=CEkmitl702;encrypt=true;hostNameInCertificate=*.database.windows.net;loginTimeout=30;";
		Connection conn = DriverManager.getConnection(url);
		if (conn != null) {
			return conn;
		} else {
			return conn;
		}
	}

	@Override
	public boolean disconnect(Connection conn) {
		try {
			conn.close();
			return true;
		} catch (SQLException e) {
			return false;
		}
	}
}
