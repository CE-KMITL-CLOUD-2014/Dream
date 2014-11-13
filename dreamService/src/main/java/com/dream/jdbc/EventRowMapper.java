package com.dream.jdbc;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import com.dream.model.Event;

public class EventRowMapper implements RowMapper<Event> {

	@Override
	public Event mapRow(ResultSet resultSet, int line) throws SQLException {
		EventExtractor event = new EventExtractor();
		return event.extractData(resultSet);
	}

}
