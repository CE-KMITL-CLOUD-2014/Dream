package com.dream.jdbc;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.ResultSetExtractor;

import com.dream.model.Event;

public class EventExtractor implements ResultSetExtractor<Event> {

	@Override
	public Event extractData(ResultSet resultSet) throws SQLException,
			DataAccessException {
		Event event = new Event(resultSet.getInt(1),resultSet.getString(4),resultSet.getString(2),
				resultSet.getDate(3) );
		return event;
	}
}
