package com.microservices.spring.cloud.app.account.ws;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@SpringBootApplication
@EnableDiscoveryClient
public class AccountWsApplication {

    public static void main(String[] args) {
        SpringApplication.run(AccountWsApplication.class, args);
    }

}
