package com.mcw.scw;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.transaction.annotation.EnableTransactionManagement;
@EnableFeignClients
@EnableTransactionManagement
@EnableDiscoveryClient
@MapperScan("com.mcw.scw.user.mapper")
@SpringBootApplication
public class ScwUserApplication {

    public static void main(String[] args) {
        SpringApplication.run(ScwUserApplication.class, args);
    }

}
