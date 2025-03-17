package com.lz.MQ;

import com.lz.pojo.result.Result;
import org.junit.jupiter.api.Test;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;


@SpringBootTest
public class mqTest {
    @Autowired
    private RabbitTemplate rabbitTemplate;

    @Test
    public void testSimpleQueue() {
        // 队列名称
        String queueName = "simple.queue";
        // 消息
        String message = "hello, spring amqp!";
        // 发送消息
        rabbitTemplate.convertAndSend(queueName, message);
    }

    @Test
    public void testFanoutExchange() {
        // 队列名称
        String exchangeName = "lz.fanout";
        // 消息
        String message = "hello, everyone!testMq";
        rabbitTemplate.convertAndSend(exchangeName, "", message);
    }

    @Test
    public void testSendDirectExchange() {
        // 交换机名称
        String exchangeName = "lz.direct";
        // 消息
        String message = "红色警报！日本乱排核废水，导致海洋生物变异，惊现哥斯拉！";
        // 发送消息
        rabbitTemplate.convertAndSend(exchangeName, "red", message);
    }

    @Test
    public void testSendDirectExchange2() {
        // 交换机名称
        String exchangeName = "lz.direct";
        // 消息
        String message = "黄色警报！日本乱排核废水，导致海洋生物变异，惊现哥斯拉！";
        // 发送消息
        rabbitTemplate.convertAndSend(exchangeName, "yellow", message);
    }

    @Test
    public void testSendDirectExchange3() {
        // 交换机名称
        String exchangeName = "lz.direct";
        // 消息
        String message = "黄色警报！日本乱排核废水，导致海洋生物变异，惊现哥斯拉！";
        // 发送消息
        rabbitTemplate.convertAndSend(exchangeName, "blue", message);
    }

    /**
     * topicExchange
     */
    @Test
    public void testSendTopicExchange() {
        // 交换机名称
        String exchangeName = "lz.topic";
        // 消息
        String message = "喜报！孙悟空大战哥斯拉，胜!";
        // 发送消息
        rabbitTemplate.convertAndSend(exchangeName, "china.news", message);
    }


    @Test
    public void testSendTopicExchange2() {
        // 交换机名称
        String exchangeName = "json.topic";
        // 消息
        Result<String> stringResult = new Result<>();

        stringResult.setMsg("Java序列化");
        stringResult.setCode(1);
        stringResult.setData("GG");
        // 发送消息
        rabbitTemplate.convertAndSend(exchangeName,  stringResult);
    }


}
