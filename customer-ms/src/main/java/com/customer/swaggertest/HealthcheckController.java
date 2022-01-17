package com.customer.swaggertest;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HealthcheckController {
	
	@RequestMapping(method = RequestMethod.GET, value = "/api/customer/health")
	public String sayHello() {
		return "I am alive";
	}
}
