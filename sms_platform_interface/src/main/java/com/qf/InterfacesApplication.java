package com.qf;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.cloud.openfeign.EnableFeignClients;

/**
 * @author cenyu
 */
@SpringBootApplication
@EnableEurekaClient
@EnableFeignClients
public class InterfacesApplication {
    public static void main(String[] args) {
        SpringApplication.run(InterfacesApplication.class, args);
    }
}