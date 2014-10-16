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
package com.dream.model;

public class Debt {
	private double	moneyLoan;
	private double	debtRate;
	private double	time;

	public Debt(double moneyLoan, double debtRate, double time) {
		this.moneyLoan = moneyLoan;
		this.debtRate = debtRate;
		this.time = time;
	}

	public double calculate() {
		double tempV, tempI, resultV;
		tempV = (1 / (1 + (this.debtRate / 1200)));
		resultV = tempV;
		for (int i = 1; i < this.time; i++) {
			resultV *= tempV;
		}
		tempI = this.debtRate / 1200;
		return (tempI / (1 - resultV)) * this.moneyLoan;
	}

	public double getMoneyLoan() {
		return moneyLoan;
	}

	public void setMoneyLoan(double moneyLoan) {
		this.moneyLoan = moneyLoan;
	}

	public double getDebtRate() {
		return debtRate;
	}

	public void setDebtRate(double debtRate) {
		this.debtRate = debtRate;
	}

	public double getTime() {
		return time;
	}

	public void setTime(double time) {
		this.time = time;
	}

}
