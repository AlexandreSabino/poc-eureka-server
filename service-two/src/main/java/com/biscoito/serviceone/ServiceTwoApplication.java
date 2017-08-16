package com.biscoito.serviceone;

import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.cloud.netflix.feign.EnableFeignClients;

@EnableEurekaClient
@SpringBootApplication
@EnableDiscoveryClient
@EnableFeignClients
public class ServiceTwoApplication {

	public static void main(String[] args) {
		new SpringApplicationBuilder(ServiceTwoApplication.class).web(true).run(args);
	}
}
