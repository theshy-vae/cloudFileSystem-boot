package com.hyj.cloud;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@MapperScan("com.hyj.cloud.mapper")
@SpringBootApplication
public class CloudFileSystem {
    public static void main(String[] args) {
        SpringApplication.run(CloudFileSystem.class, args);
    }
}
