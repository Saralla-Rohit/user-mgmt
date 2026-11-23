package com.phegondev.usersmanagementsystem;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;



@SpringBootApplication
public class UsersmanagementsystemApplication {
	
	public static void main(String[] args) {
		SpringApplication app = new SpringApplication(UsersmanagementsystemApplication.class);

        //   ctx.getEnvironment().setActiveProfiles("dev")
        // becomes:
        app.setAdditionalProfiles("dev");

        // This IS your rohit ctx.refresh() + ctx.register(...) combined:
        //   ctx.register(...) → Boot already registers everything via @SpringBootApplication
        //   ctx.refresh()     → app.run(...) does the refresh
        app.run(args);
	}

}
