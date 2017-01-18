package com.blogservice.rabbit;

import org.springframework.amqp.core.*;
import org.springframework.amqp.core.Message;

/**
 * Created by z003rn5u on 17.01.2017.
 */
public class Consumer implements MessageListener {
    @Override
    public void onMessage(Message message) {
        System.out.println(new String(message.getBody()));
    }
}
