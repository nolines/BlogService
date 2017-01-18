package com.blogservice.configuration;

import com.blogservice.rabbit.Consumer;
import org.springframework.amqp.core.AcknowledgeMode;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.TopicExchange;
import org.springframework.amqp.rabbit.connection.CachingConnectionFactory;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.rabbit.listener.SimpleMessageListenerContainer;
import org.springframework.amqp.support.converter.JsonMessageConverter;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

/**
 * Created by z003rn5u on 17.01.2017.
 */
@Configuration
@ComponentScan("com.blogservice.configuration.RabbitMQConfiguration")
public class RabbitMQConfiguration {

    @Value("${exchangeName}")
    private String sendLogsExchangeName;

    @Value("${rabbitmq.user}")
    private String RMQUsername;

    @Value("${rabbitmq.pass}")
    private String RMQPassword;

    @Value("${rabbitmq.hostName}")
    private String HostName;

    @Bean
    public ConnectionFactory connectionFactory() {
        CachingConnectionFactory connectionFactory = new CachingConnectionFactory(HostName);
        connectionFactory.setUsername(RMQUsername);
        connectionFactory.setPassword(RMQPassword);
        return connectionFactory;
    }

    @Bean
    public TopicExchange sendLogsExchange(){
        return new TopicExchange(sendLogsExchangeName, false,false);
    }

    @Bean
    public Queue simpleQueue() {
        return new Queue(sendLogsExchangeName);
    }

    @Bean
    public MessageConverter jsonMessageConverter(){
        return new JsonMessageConverter();
    }

    @Bean
    public RabbitTemplate rabbitTemplate() {
        RabbitTemplate template = new RabbitTemplate(connectionFactory());
        template.setRoutingKey(sendLogsExchangeName);
        template.setMessageConverter(jsonMessageConverter());
        return template;
    }

    @Bean
    public SimpleMessageListenerContainer listenerContainer() {
        SimpleMessageListenerContainer listenerContainer = new SimpleMessageListenerContainer();
        listenerContainer.setConnectionFactory(connectionFactory());
        listenerContainer.setQueues(simpleQueue());
        listenerContainer.setMessageConverter(jsonMessageConverter());
        listenerContainer.setMessageListener(new Consumer());
        listenerContainer.setAcknowledgeMode(AcknowledgeMode.AUTO);
        return listenerContainer;
    }
}
