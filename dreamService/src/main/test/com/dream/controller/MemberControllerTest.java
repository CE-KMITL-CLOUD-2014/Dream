package com.dream.controller;

import static org.hamcrest.Matchers.*;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import java.sql.Timestamp;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.jdbc.JdbcDaoImpl;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import com.dream.model.Member;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "file:src/main/webapp/WEB-INF/rest-servlet.xml" })
@WebAppConfiguration
public class MemberControllerTest {
	@Autowired
	private WebApplicationContext webApplicationContext;
	private MemberController memberControllerMock = mock(MemberController.class);
	static JdbcDaoImpl userDetailsService = null;
	private MockMvc mockMvc;
	private Authentication authToken;

	@Before
	public void setup() {
		userDetailsService = webApplicationContext.getBean(JdbcDaoImpl.class);
		UserDetails userDetails = userDetailsService
				.loadUserByUsername("admin");
		authToken = new UsernamePasswordAuthenticationToken(userDetails,
				userDetails.getUsername());
		SecurityContextHolder.getContext().setAuthentication(authToken);
		mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext)
				.build();
	}

	@Test
	public void testList() throws Exception {
		mockMvc.perform(get("/member/list")).andExpect(status().isOk());
	}

	@Test
	public void testFinduserAfterLogin() throws Exception {
		mockMvc.perform(get("/member/findfromuser"))
				.andExpect(status().isOk())
				.andExpect(
						content().contentType(TestUtil.APPLICATION_JSON_UTF8))
				.andExpect(jsonPath("$.username", is("admin")))
				.andExpect(jsonPath("$.email", is("admin@hotmail.com")))
				.andExpect(jsonPath("$.phone", is("0881234567")))
				.andExpect(jsonPath("$.fname", is("admin")))
				.andExpect(jsonPath("$.lname", is("admin")))
				.andExpect(jsonPath("$.birth", is("1992-11-01")))
				.andExpect(jsonPath("$.nickname", is("admin")));
	}
}
