package com.atguigu.aclservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.ComponentScan;

/**
 * @author wu on 2020/8/26 0026
 */
@EnableFeignClients
@EnableDiscoveryClient
@SpringBootApplication
@ComponentScan(basePackages = "com.atguigu")
public class AclServiceApplication {
    public static void main(String[] args) {
        SpringApplication.run(AclServiceApplication.class, args);
    }
}
