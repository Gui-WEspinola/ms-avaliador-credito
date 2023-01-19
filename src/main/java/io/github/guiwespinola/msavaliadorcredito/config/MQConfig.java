package io.github.guiwespinola.msavaliadorcredito.config;

import org.springframework.amqp.core.Queue;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class MQConfig {

    @Value("${mq.queues.issue-card}")
    private String cardRequestQueue;

    @Bean
    public Queue queueCardRequest() {
        return new Queue(cardRequestQueue, true);
    }
}
