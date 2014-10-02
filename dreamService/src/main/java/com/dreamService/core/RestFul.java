package com.dreamService.core;

import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/hello/test")
public class RestFul {
	@RequestMapping(value = "/{name}", method = RequestMethod.GET,headers="Accept=application/json")
	public String getGreeting(@PathVariable String name) {
		String result = "Hello " + name;
		return result;
	}
}
