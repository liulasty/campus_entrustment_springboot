package com.lz;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

/*
 * Created with IntelliJ IDEA.
 * @Author: lz
 * @Date: 2024/04/04/17:44
 * @Description:
 */

import lombok.extern.slf4j.Slf4j;

/**
 * @author lz
 */
@SpringBootApplication
@EnableScheduling
@Slf4j
public class Application {
    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
        log.info("server started");
    }
}