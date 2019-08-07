package com.miu.producer.controller;

import org.apache.activemq.ActiveMQConnectionFactory;

import javax.jms.*;

/**
 * @Description:ActiveMQ的消息持久化到Mysql数据库
 * @Author: gaoxi
 * @CreateDate: 2019/8/7 18:23
 * @Version: 1.0
 * https://blog.csdn.net/zbw18297786698/article/details/52999940
 */
public class Sender {
    public static void main(String[] args) throws JMSException {
        // 1、建立ConnectionFactory工厂对象，需要填入用户名，密码，以及连接的地址
        // 仅使用默认。端口号为"tcp://localhost:61616"
        ConnectionFactory connectionFactory = new ActiveMQConnectionFactory(
                "admin",// ActiveMQConnectionFactory.DEFAULT_USER,
                "admin",// ActiveMQConnectionFactory.DEFAULT_PASSWORD,
                "tcp://localhost:61616");
        // 2、通过ConnectionFactory工厂对象创建一个Connection连接
        // 并且调用Connection的start方法开启连接,Connection默认是不开启的
        Connection connection = connectionFactory.createConnection();
        connection.start();

        // 3、通过Connection对象创建Session会话（上下文环境对象），
        // 参数一，表示是否开启事务
        // 参数二，表示的是签收模式，一般使用的有自动签收和客户端自己确认签收

        // 第一个参数设置为true，表示开启事务
        // 开启事务后，记得要手动提交事务

        Session session = connection.createSession(Boolean.TRUE,
                Session.CLIENT_ACKNOWLEDGE);

        // 4、通过Session创建Destination对象，指的是一个客户端用来指定生产消息目标和消费消息来源的对象。
        // 在PTP模式中,Destination指的是Queue
        // 在发布订阅模式中，Destination指的是Topic
        Destination destination = session.createQueue("queue1");


        // 5、使用Session来创建消息对象的生产者或者消费者
        MessageProducer messageProducer = session.createProducer(destination);

        // 6、如果是，生产者，使用MessageProducer的setDeliverMode方法设置，消息的持久化和非持久化
        messageProducer.setDeliveryMode(DeliveryMode.PERSISTENT);
        // 7、最后使用JMS规范的TextMessage形式创建数据(通过Session对象)
        // 并利用MessageProducer的send方法发送数据
        for (int i = 0; i < 5; i++) {
            TextMessage textMessage = session.createTextMessage();
            textMessage.setText("我是消息" + i);
            messageProducer.send(textMessage);
        }

        // 手动提交开启的事务
        session.commit();

        // 释放连接
        if (connection != null) {
            connection.close();
        }

    }
}