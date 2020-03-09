package com.mcw.scw;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.circuitbreaker.EnableCircuitBreaker;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@EnableTransactionManagement
@EnableFeignClients
@EnableCircuitBreaker
@EnableDiscoveryClient
@MapperScan("com.mcw.scw.order.mapper")
@SpringBootApplication
public class ScwOrderApplication {

    public static void main(String[] args) {
        SpringApplication.run(ScwOrderApplication.class, args);
    }

}
