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

import com.dream.model.Saving;

public class SavingExtractor implements ResultSetExtractor<Saving> {

	@Override
	public Saving extractData(ResultSet resultSet) throws SQLException,
			DataAccessException {
		Saving saving = new Saving(resultSet.getInt("#save"),resultSet.getString("username"), resultSet.getDouble("goal"),
				resultSet.getDouble("start_amount"), resultSet.getString("end_time"),
				resultSet.getString("description"));
		return saving;
	}

}
