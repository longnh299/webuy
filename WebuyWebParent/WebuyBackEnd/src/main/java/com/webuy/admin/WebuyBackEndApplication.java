package com.webuy.admin;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;

@SpringBootApplication
@EntityScan({"com.webuy.common.entity", "com.webuy.admin.user"})
public class WebuyBackEndApplication {

	public static void main(String[] args) {
		SpringApplication.run(WebuyBackEndApplication.class, args);
	}

}
