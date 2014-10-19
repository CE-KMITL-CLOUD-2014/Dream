package com.dream.dao;

import com.dream.model.Finance;

public interface FinanceDAO {
	public int insert(Finance finance);

	public int update(Finance finance);

	public Finance findFromUsername(String username);

	public boolean delete(String username);
}
