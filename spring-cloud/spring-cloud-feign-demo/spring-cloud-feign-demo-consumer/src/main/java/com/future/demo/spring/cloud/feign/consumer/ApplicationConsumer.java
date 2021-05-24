package com.future.demo.spring.cloud.feign.consumer;

import com.future.demo.spring.cloud.feign.common.feign.ProductFeign;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@EnableFeignClients(
        clients = {
                ProductFeign.class
        }
)
public class ApplicationConsumer {
    public static void main(String[] args) {
        SpringApplication.run(ApplicationConsumer.class, args);
    }
}
