package com.phegondev.usersmanagementsystem.profiles;

import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.context.annotation.PropertySource;

@Configuration
@Profile("dev")
@PropertySource("application-dev.properties")
public class DevProfile {

}
