package com.dream.config;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.ImportResource;

@Configuration
@ImportResource({ "classpath:DAOConfig.xml" })
@ComponentScan("com.dream.dao")
public class SpringDAOConfig {
	public SpringDAOConfig() {
		super();
	}
}
