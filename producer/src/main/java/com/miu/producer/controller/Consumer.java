package com.miu.producer.controller;

import org.apache.activemq.ActiveMQConnectionFactory;

import javax.jms.*;

/**
 * @Description:消费持久化到mysql中的消息
 * 与Sender对应。Sender像数据库持久化消息，Consumer模拟断电恢复后继续消费消息
 * 持久化的一个好处就是防止断电重连后消息消失
 * https://www.cnblogs.com/grey-wolf/p/6530998.html
 * @Author: gaoxi
 * @CreateDate: 2019/8/7 18:53
 * @Version: 1.0
 */
public class Consumer {
    public static void main(String[] args) throws JMSException {
        // Create a ConnectionFactory
        ActiveMQConnectionFactory connectionFactory = new ActiveMQConnectionFactory(
                "tcp://localhost:61616");
        // Create a Connection
        Connection connection = connectionFactory.createConnection();
        connection.start();
        // Create a Session
        Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
        // Create the destination (Topic or Queue)
        Destination destination = session.createQueue("queue1");
        // Create a MessageConsumer from the Session to the Topic or
        // Queue
        MessageConsumer consumer = session.createConsumer(destination);
        // Wait for a message
        Message message = consumer.receive(1000);
        if (message instanceof TextMessage) {
            TextMessage textMessage = (TextMessage) message;
            String text = textMessage.getText();
            System.out.println("Received: " + text);
        } else {
            System.out.println("Received: " + message);
        }

        consumer.close();
        session.close();
        connection.close();
    }
}