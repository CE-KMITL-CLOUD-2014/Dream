package com.dream.database;

import java.sql.SQLException;

import com.dream.user.User;

public class JDBC_QueryUser extends JDBC implements Query_User {

	@Override
	public User queryUser(User user) {
		JDBC jdbc = new JDBC();
		if (jdbc.jdbcConnect()) {
			System.out.println("can't connect");
		}
		// statement to insert USER
		String cmd = "select * from customer";
		try {
			jdbc.resultSet = jdbc.statement.executeQuery(cmd);
			System.out.println(resultSet = null);
			while (jdbc.resultSet.next()) {
				System.out.println(jdbc.resultSet.getString(1));
			}

		} catch (SQLException e) {
			e.printStackTrace();
		}
		// close connection //
		jdbc.jdbcDisconnect();
		return null;
	}

}
