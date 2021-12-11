package com.horvee.storylog.service.ke;

import com.horvee.storylog.springboot.configuration.annotation.EnableStoryLog;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;


@EnableStoryLog
@SpringBootApplication
public class KEApplication {

    public static void main(String[] args) {
        SpringApplication.run(KEApplication.class,args);
    }

}
