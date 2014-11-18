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

package com.dream.util;

import java.sql.Date;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Locale;

/**
 * For change string date in format yyyy-MM-dd to Date object
 * 
 * @author shadowslight
 *
 */
public class DreamUtil {
	private static SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd",
			Locale.US);

	/**
	 * Get Date by chang string to Date
	 * 
	 * @param time
	 *            date in formate yyyy-MM-dd
	 * @return Date object
	 */
	public static Date getCurrentDate(String time) {
		if (time != null) {
			Date date = null;
			try {
				date = new Date(format.parse(time).getTime());
			} catch (ParseException e) {
				e.printStackTrace();
			}
			return date;
		} else {
			return null;
		}

	}
}
