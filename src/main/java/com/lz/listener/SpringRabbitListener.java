//package com.lz.listener;
//
//import com.lz.pojo.result.Result;
//import org.springframework.amqp.core.ExchangeTypes;
//import org.springframework.amqp.rabbit.annotation.Exchange;
//import org.springframework.amqp.rabbit.annotation.Queue;
//import org.springframework.amqp.rabbit.annotation.QueueBinding;
//import org.springframework.amqp.rabbit.annotation.RabbitListener;
//import org.springframework.stereotype.Component;
//
//@Component
//public class SpringRabbitListener {
//
//    @RabbitListener(queues = "simple.queue")
//    public void listenSimpleQueueMessage(String msg) throws InterruptedException {
//        System.out.println("spring 消费者接收到消息：【" + msg + "】");
//    }
//
//    @RabbitListener(queues = "simple.queue")
//    public void listenSimpleQueueMessage2(String msg) throws InterruptedException {
//        System.out.println("spring 消费者接收到消息：【" + msg + "】");
//    }
//
//    @RabbitListener(queues = "lz.queue1")
//    public void listenFanoutQueue1(String msg) {
//        System.out.println("消费者1接收到Fanout消息：【" + msg + "】");
//    }
//
//    @RabbitListener(queues = "lz.queue2")
//    public void listenFanoutQueue2(String msg) {
//        System.out.println("消费者2接收到Fanout消息：【" + msg + "】");
//    }
//
//    @RabbitListener(bindings = @QueueBinding(
//            value = @Queue(name = "direct.queue1"),
//            exchange = @Exchange(name = "lz.direct", type = ExchangeTypes.DIRECT),
//            key = {"red", "blue"}
//    ))
//    public void listenDirectQueue1(String msg){
//        System.out.println("消费者接收到direct.queue1的消息：【" + msg + "】");
//    }
//
//    @RabbitListener(bindings = @QueueBinding(
//            value = @Queue(name = "direct.queue2"),
//            exchange = @Exchange(name = "lz.direct", type = ExchangeTypes.DIRECT),
//            key = {"red", "yellow"}
//    ))
//    public void listenDirectQueue2(String msg){
//        System.out.println("消费者接收到direct.queue2的消息：【" + msg + "】");
//    }
//
//    @RabbitListener(bindings = @QueueBinding(
//            value = @Queue(name = "topic.queue1"),
//            exchange = @Exchange(name = "lz.topic", type = ExchangeTypes.TOPIC),
//            key = "china.#"
//    ))
//    public void listenTopicQueue1(String msg){
//        System.out.println("消费者接收到topic.queue1的消息：【" + msg + "】");
//    }
//
//    @RabbitListener(bindings = @QueueBinding(
//            value = @Queue(name = "topic.queue2"),
//            exchange = @Exchange(name = "lz.topic", type = ExchangeTypes.TOPIC),
//            key = "#.news"
//    ))
//    public void listenTopicQueue2(String msg){
//        System.out.println("消费者接收到topic.queue2的消息：【" + msg + "】");
//    }
//
//    @RabbitListener(queues = "json.topic")
//    public void listenJsonQueue(Result<String> msg) {
//        System.out.println("消费者1接收到Fanout消息：【" + msg + "】");
//    }
//}
