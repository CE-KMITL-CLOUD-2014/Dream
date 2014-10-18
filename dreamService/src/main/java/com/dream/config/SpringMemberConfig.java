package com.dream.config;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.ImportResource;

@Configuration
@ImportResource({ "classpath:MemberConfig.xml" })
@ComponentScan("com.dream.dao.impl")
public class SpringMemberConfig {
	public SpringMemberConfig() {
		super();
	}
}
