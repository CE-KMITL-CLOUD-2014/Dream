/****************************************************************************
 * Copyright Peerawit Praphanwittaya & Apirat Puttaraksa                    * 
 *                                                                          * 
 * Licensed under the Apache License, Version 2.0 (the "License");          * 
 * you may not use this file except in compliance with the License.         * 
 * You may obtain a copy of the License at                                  * 
 * 																		    * 
 *     http://www.apache.org/licenses/LICENSE-2.0                           * 
 * 																		    * 
 * Unless required by applicable law or agreed to in writing, software	    * 
 * distributed under the License is distributed on an "AS IS" BASIS,        * 
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. * 
 * See the License for the specific language governing permissions and      * 
 * limitations under the License.                                           * 
 */

package com.dream.jdbc;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import com.dream.model.Finance;

/**
 * For map Finance data from database to Row
 * 
 * @author Peerawit Praphanwittaya
 *
 */
public class FinanaceRowMapper implements RowMapper<Finance> {

	@Override
	public Finance mapRow(ResultSet resultSet, int line) throws SQLException {
		FinanceExtractor finance = new FinanceExtractor();
		return finance.extractData(resultSet);
	}

}
