package com.dreamService.core;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.ImportResource;

@Configuration
@ImportResource({ "classpath:webSecurityConfig.xml" })
@ComponentScan("com.dreamService.core.security")
public class SpringSecurityConfig {
 
    public SpringSecurityConfig() {
        super();
    }
 
}
