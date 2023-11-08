package com.nijin.customer;

import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

@Configuration
public class CustomerConfig {

    @Bean
    @LoadBalanced //Removes the ambigity in connecting to hosts in Eureka Server, will redirect to any available host
    public RestTemplate restTemplate(){
        return new RestTemplate();
    }
}
