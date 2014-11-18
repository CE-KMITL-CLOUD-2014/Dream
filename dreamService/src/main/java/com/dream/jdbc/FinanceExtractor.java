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

import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.ResultSetExtractor;

import com.dream.model.Finance;

/**
 * For map Finance data from database to Finance Object
 * 
 * @author Peerawit Praphanwittaya
 *
 */
public class FinanceExtractor implements ResultSetExtractor<Finance> {

	@Override
	public Finance extractData(ResultSet resultSet) throws SQLException,
			DataAccessException {
		Finance finance = new Finance(resultSet.getInt(2),
				resultSet.getDouble(4), resultSet.getString("description"),
				resultSet.getTimestamp("date_time"),
				resultSet.getString("username"), resultSet.getInt("#budget"),
				resultSet.getInt("#save"), resultSet.getInt("#event"),
				resultSet.getInt("type"), resultSet.getString(11));
		return finance;
	}

}
