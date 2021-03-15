package com.zhouxh.rocketmq.control;

import org.apache.rocketmq.client.producer.SendResult;
import org.apache.rocketmq.spring.core.RocketMQTemplate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessageHeaders;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;


/**
 * @author zhouxh-z
 */
@RestController
public class ProducerControl {
    Logger logger = LoggerFactory.getLogger(ProducerControl.class);
    @Resource
    RocketMQTemplate rocketMQTemplate;
    /**
     * 如果需要通过send方法，发送消息，需要设置默认的 destination
     * rocketMQTemplate.setDefaultDestination("topic_test:tagXXX");
     * Message message = MessageBuilder.createMessage("hello-world", new MessageHeaders(new HashMap<>()));
     * rocketMQTemplate.send(message);
     *
     * convertAndSend()
     *  springboot对rocketmq API 的封装
     *  方法参数
     *      destination：topic:tag 的拼凑，底层会根据 ":" 来分割
     *      payload：荷载 ，实际发送的内容
     */
    @RequestMapping("/sendMSG")
    public String sendMSG(){
        // 普通消息
        rocketMQTemplate.convertAndSend("topic_test:tagXXX","hello-world");
        logger.info("普通消息发送成功");
        // 顺序消息
        for(int i = 0;i<10;i++){
            SendResult sendResult = rocketMQTemplate.syncSendOrderly("topic_test", "order_message_" + i, "");
            logger.info("顺序消息:{},发送状态:{}",sendResult.getMsgId(),sendResult.getSendStatus());
        }
        /**
         * 广播消息: 和生产者和普通发消息一致，消费者在消费时，messageModel需要设置为 BROADCASTING 广播模式
         */

        // 延迟消息--消息发送时设置延时等级 delayLevel
        // syncSend(String destination, Message<?> message, long timeout, int delayLevel)
        Message delayedMessage = MessageBuilder.createMessage("delayed_message", new MessageHeaders(new HashMap<>()));
        rocketMQTemplate.syncSend("topic_test",delayedMessage,3000,1);
        logger.info("延时消息发送成功");

        // 批量消息：将多个消息合并成一个批量消息，一次发送
        List<Message> messageList = new ArrayList<Message>();
        for(int i = 0;i<10;i++){
            messageList.add(MessageBuilder.createMessage("batch_message_"+i, new MessageHeaders(new HashMap<>())));
        }
        rocketMQTemplate.syncSend("topic_test",messageList);
        logger.info("批量消息发送成功");

        // 过滤消息：主要是消费者端过滤，设置tag 或者 sql92 模式
        Message<String> tag_message = MessageBuilder.createMessage("tag_message",
                new MessageHeaders(new HashMap<>()));
        rocketMQTemplate.syncSend("topic_test:tag_A",tag_message);
        logger.info("过滤消息发送成功");

        // 事务消息
        Message message = MessageBuilder.createMessage("transaction_message", new MessageHeaders(new HashMap<>()));
        rocketMQTemplate.sendMessageInTransaction("topic_test",message,"");
        logger.info("事务消息发送成功");

        rocketMQTemplate.getProducer().setProducerGroup("");
        return "发送成功";
    }
}
