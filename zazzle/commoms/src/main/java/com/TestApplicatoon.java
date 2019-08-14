package com;


import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.context.annotation.Configuration;

@SpringBootApplication
@Configuration
public class TestApplicatoon extends  org.springframework.boot.web.servlet.support.SpringBootServletInitializer{
    public static void main(String[] args) {

        SpringApplication.run(TestApplicatoon.class, args);
    }

    @Override
    protected SpringApplicationBuilder configure(SpringApplicationBuilder builder) {
        return builder.sources(TestApplicatoon.class);
    }
}
