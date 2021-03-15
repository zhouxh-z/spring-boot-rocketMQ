package com.zhouxh.rocketmq.consumer;


import org.apache.rocketmq.spring.annotation.RocketMQMessageListener;
import org.apache.rocketmq.spring.core.RocketMQListener;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

/**
 * @author zhouxh-z
 */
@Component
@RocketMQMessageListener(consumerGroup = "topic_test-consumerGroup", topic = "topic_test")
public class Consumer implements RocketMQListener<String> {
    Logger logger = LoggerFactory.getLogger(Consumer.class);
    @Override
    public void onMessage(String message) {
        logger.info("消费消息:"+message);
    }
}
