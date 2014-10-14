package com.dreamService.core.database;

import java.sql.Connection;
import java.sql.SQLException;

public interface ConnectDB {
	public Connection connect() throws SQLException;
}
