package com.zhouxh.rocketmq.config;

import org.slf4j.Logger;
import org.apache.rocketmq.spring.annotation.RocketMQTransactionListener;
import org.apache.rocketmq.spring.core.RocketMQLocalTransactionListener;
import org.apache.rocketmq.spring.core.RocketMQLocalTransactionState;
import org.slf4j.LoggerFactory;
import org.springframework.messaging.Message;

/**
 * @author zhouxh-z
 */
@RocketMQTransactionListener()
public class MyTransactionListener implements RocketMQLocalTransactionListener {
    Logger logger = LoggerFactory.getLogger(MyTransactionListener.class);

    /**
     *
     * @param message
     * @param o
     * @return
     */
    @Override
    public RocketMQLocalTransactionState executeLocalTransaction(Message message, Object o) {
        logger.info("executeLocalTransaction:"+message);
        return RocketMQLocalTransactionState.UNKNOWN;
    }


    @Override
    public RocketMQLocalTransactionState checkLocalTransaction(Message message) {
        logger.info("checkLocalTransaction:"+message);
        return RocketMQLocalTransactionState.COMMIT;
    }
}
