package com.zhouxh.rocketmq;

import org.apache.rocketmq.spring.core.RocketMQTemplate;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Scope;

/**
 * @author zhouxh-z
 */
@SpringBootApplication
public class RocketMQApplication {

    public static void main(String[] args) {
        SpringApplication.run(RocketMQApplication.class, args);
    }
}
