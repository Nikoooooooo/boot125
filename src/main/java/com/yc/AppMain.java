package com.yc;

import lombok.extern.slf4j.Slf4j;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;
import springfox.documentation.oas.annotations.EnableOpenApi;

@SpringBootApplication   //boot启动类配置
@Slf4j
@MapperScan("com.yc.mappers")
@EnableOpenApi
@EnableScheduling
public class AppMain {
    public static void main(String[] args) throws InterruptedException {
        SpringApplication.run(AppMain.class, args);


    }
}
