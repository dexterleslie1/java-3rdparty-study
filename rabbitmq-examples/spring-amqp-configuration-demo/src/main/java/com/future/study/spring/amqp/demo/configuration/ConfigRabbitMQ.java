package com.future.study.spring.amqp.demo.configuration;

import org.springframework.amqp.core.*;
import org.springframework.amqp.rabbit.connection.CachingConnectionFactory;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.rabbit.listener.SimpleMessageListenerContainer;
import org.springframework.amqp.rabbit.listener.adapter.MessageListenerAdapter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author Dexterleslie.Chan
 */
@Configuration
public class ConfigRabbitMQ {
    static final String rabbitMQHost = "192.168.1.158";
    static final int rabbitMQPort = 5672;
    static final String rabbitMQUsername = "guest";
    static final String rabbitMQPassword = "guest";

    public static final String topicExchangeName = "spring-boot-exchange";
    static final String queueName = "spring-boot";

    @Bean
    public ConnectionFactory connectionFactory(){
        CachingConnectionFactory connectionFactory =
                new CachingConnectionFactory(rabbitMQHost, rabbitMQPort);
        connectionFactory.setUsername(rabbitMQUsername);
        connectionFactory.setPassword(rabbitMQPassword);
        return connectionFactory;
    }

    @Bean
    public AmqpTemplate amqpTemplate(ConnectionFactory connectionFactory){
        AmqpTemplate amqpTemplate = new RabbitTemplate(connectionFactory);
        return amqpTemplate;
    }

    @Bean
    Queue queue() {
        return new Queue(queueName, false, false, true);
    }

    @Bean
    TopicExchange exchange() {
        return new TopicExchange(topicExchangeName, false, true);
    }

    @Bean
    Binding binding(Queue queue, TopicExchange exchange) {
        return BindingBuilder.bind(queue).to(exchange).with("foo.bar.*");
    }

    @Bean
    SimpleMessageListenerContainer container(ConnectionFactory connectionFactory,
                                             MessageListenerAdapter listenerAdapter) {
        SimpleMessageListenerContainer container = new SimpleMessageListenerContainer();
        container.setConnectionFactory(connectionFactory);
        container.setQueueNames(queueName);
        container.setMessageListener(listenerAdapter);
        return container;
    }

    @Bean
    MessageListenerAdapter listenerAdapter(Receiver receiver) {
        return new MessageListenerAdapter(receiver, "receiveMessage");
    }
}