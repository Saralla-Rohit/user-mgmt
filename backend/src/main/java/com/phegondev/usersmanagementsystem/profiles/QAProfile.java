package com.phegondev.usersmanagementsystem.profiles;

import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.context.annotation.PropertySource;

@Configuration
@Profile("qa")
@PropertySource("application-qa.properties")
public class QAProfile {

}
