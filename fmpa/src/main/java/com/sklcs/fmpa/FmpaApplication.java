package com.sklcs.fmpa;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@SpringBootApplication
// Swagger
@EnableSwagger2

public class FmpaApplication {

    public static void main(String[] args) {
        SpringApplication.run(FmpaApplication.class, args);
    }

}
