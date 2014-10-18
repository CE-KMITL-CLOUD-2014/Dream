package com.dream.database;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class JDBC extends Database {
	protected Connection	conn;
	protected Statement		statement;
	protected ResultSet		resultSet;

	public boolean jdbcConnect() {
		try {
			connectDB = new JDBCDriver();
			conn = connectDB.connect();

			statement = conn.createStatement();
			resultSet = statement
					.executeQuery("SELECT Distinct TABLE_NAME FROM information_schema.TABLES");
			while (resultSet.next()) {
				System.out.println("name=" + resultSet.getString(1));
			}
			return statement == null ? true : false;
		} catch (SQLException e) {
			return false;
		}
	}

	public boolean jdbcDisconnect() {
		try {
			conn.close();
			return true;
		} catch (SQLException e) {
			return false;
		}
	}
}
