package com.miu.consumera.listener;

import org.springframework.jms.annotation.JmsListener;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Component;

/**
 * @Description:
 * @Author: gaoxi
 * @CreateDate: 2019/8/7 15:06
 * @Version: 1.0
 */
@Component
public class QueueListener {
    /**
     * SendTo 会将此方法返回的数据, 写入到 queue : out.queue 中去.
     * @param text
     * @return
     */
    @JmsListener(destination = "publish.queue", containerFactory = "jmsListenerContainerQueue")
    @SendTo("out.queue")
    public String receive(String text){
        System.out.println("QueueListener: consumer-a 收到一条信息: " + text);
        return "consumer-a received : " + text;
    }
}