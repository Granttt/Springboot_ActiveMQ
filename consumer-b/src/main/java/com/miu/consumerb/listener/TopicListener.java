package com.miu.consumerb.listener;

import org.springframework.jms.annotation.JmsListener;
import org.springframework.stereotype.Component;

/**
 * @Description:
 * @Author: gaoxi
 * @CreateDate: 2019/8/7 15:07
 * @Version: 1.0
 */
@Component
public class TopicListener {
    /**
     * 这里通过传入不同的factory, 来实现发送不同类型的信息.
     * @param text
     */
    @JmsListener(destination = "publish.topic", containerFactory = "jmsListenerContainerTopic")
    public void receive(String text){
        System.out.println("TopicListener: consumer-b 收到一条信息: " + text);
    }
}