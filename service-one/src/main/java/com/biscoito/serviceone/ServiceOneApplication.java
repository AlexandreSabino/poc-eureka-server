package com.biscoito.serviceone;

import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
//import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
//import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

//@EnableEurekaClient
@RestController
@SpringBootApplication
//@EnableDiscoveryClient
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class ServiceOneApplication {

	@RequestMapping("/hello")
	@PreAuthorize("hasRole('ROLE_TESTE')")
	public String home() {
		return "Hello world";
	}

	public static void main(String[] args) {
		new SpringApplicationBuilder(ServiceOneApplication.class).web(true).run(args);
	}
}
