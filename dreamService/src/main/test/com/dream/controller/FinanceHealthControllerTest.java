package com.dream.controller;

import static org.hamcrest.Matchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.jdbc.JdbcDaoImpl;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "**/DAOConfig.xml",
		"**/webSecurityConfig.xml",
		"file:src/main/webapp/WEB-INF/rest-servlet.xml" })
@WebAppConfiguration
public class FinanceHealthControllerTest {
	@Autowired
	private WebApplicationContext webApplicationContext;

	static JdbcDaoImpl userDetailsService = null;
	private MockMvc mockMvc;
	private Authentication authToken;
	UserDetails userDetails;

	@Before
	public void setup() throws Exception {
		mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext)
				.build();
	}

	@Test
	public void can_get_liquidity_correctly_when_input_data() throws Exception {
		mockMvc.perform(
				get("/financehealth/getliquidity").param("assets", "1000")
						.param("expenses", "100")).andExpect(status().isOk())
				.andExpect(jsonPath("$liquidtyValue", is(10.0)));
	}

	@Test
	public void can_get_liquidity_correctly_when_login() throws Exception {
		userDetailsService = webApplicationContext.getBean(JdbcDaoImpl.class);
		userDetails = userDetailsService.loadUserByUsername("fortest");
		authToken = new UsernamePasswordAuthenticationToken(userDetails,
				"fortest");
		SecurityContextHolder.getContext().setAuthentication(authToken);
		mockMvc.perform(get("/financehealth/getliquidity"))
				.andExpect(status().isOk()).andExpect(jsonPath("$liquidtyValue", is(3.0)));
	}

	@Test
	public void can_get_debtRatio_correctly_when_input_data() throws Exception {
		mockMvc.perform(
				get("/financehealth/debtratio").param("debt", "1000").param(
						"totalAsset", "100")).andExpect(status().isOk())
				.andExpect(jsonPath("$debtRatioValue", is(10.0)));
	}

	@Test
	public void can_get_savingratio_correctly_when_input_data()
			throws Exception {
		mockMvc.perform(
				get("/financehealth/savingratio")
						.param("savingPerYear", "1000").param("incomePerYear",
								"10000")).andExpect(status().isOk())
				.andExpect(jsonPath("$savingRatioValue", is(0.1)));
	}

	@Test
	public void when_input_data_with_zero_getSavingratio_must_return_zero()
			throws Exception {
		mockMvc.perform(
				get("/financehealth/savingratio").param("savingPerYear", "0")
						.param("incomePerYear", "0"))
				.andExpect(status().isOk()).andExpect(jsonPath("$savingRatioValue", is(0.0)));
	}

	@After
	public void clear() {
		SecurityContextHolder.clearContext();
	}
}
