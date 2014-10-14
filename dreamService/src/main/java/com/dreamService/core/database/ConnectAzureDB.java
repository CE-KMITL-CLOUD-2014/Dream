package com.dreamService.core.database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConnectAzureDB implements ConnectDB{
	@Override
	public Connection connect() throws SQLException {
		String url = "jdbc:sqlserver://y1gjo9199y.database.windows.net:1433;database=moneylogging;user=moneyAdmin@y1gjo9199y;password=Bell2535;encrypt=true;hostNameInCertificate=*.database.windows.net;loginTimeout=30;";
		Connection conn = DriverManager.getConnection(url);
		if(conn!=null){
			return conn;
		}else{
			return conn;
		}
	}

}
