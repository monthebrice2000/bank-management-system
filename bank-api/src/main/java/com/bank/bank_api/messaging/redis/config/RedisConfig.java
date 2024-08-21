package com.bank.bank_api.messaging.redis.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.listener.RedisMessageListenerContainer;
import org.springframework.data.redis.listener.adapter.MessageListenerAdapter;
import org.springframework.data.redis.serializer.Jackson2JsonRedisSerializer;

import com.bank.bank_api.domain.Transaction;
import com.bank.bank_api.messaging.redis.consumer.TransactionConsumer;

import org.springframework.data.redis.listener.PatternTopic;

@Configuration
@Profile("redis-profile")
public class RedisConfig {

    @Bean
    public RedisMessageListenerContainer container(RedisConnectionFactory connectionFactory,
            MessageListenerAdapter toDoListenerAdapter,
            @Value("${bank.redis.transaction.topic}") String topic) {
        RedisMessageListenerContainer container = new RedisMessageListenerContainer();
        container.setConnectionFactory(connectionFactory);
        container.addMessageListener(toDoListenerAdapter, new PatternTopic(topic));
        return container;
    }

    @Bean
    MessageListenerAdapter toDoListenerAdapter(TransactionConsumer consumer) {
        MessageListenerAdapter messageListenerAdapter = new MessageListenerAdapter(consumer);
        messageListenerAdapter.setSerializer(new Jackson2JsonRedisSerializer<>(Transaction.class));
        return messageListenerAdapter;
    }

    @Bean
    RedisTemplate<String, Transaction> redisTemplate(RedisConnectionFactory connectionFactory) {
        RedisTemplate<String, Transaction> redisTemplate = new RedisTemplate<String, Transaction>();
        redisTemplate.setConnectionFactory(connectionFactory);
        redisTemplate.setDefaultSerializer(new Jackson2JsonRedisSerializer<>(Transaction.class));
        redisTemplate.afterPropertiesSet();
        return redisTemplate;
    }

}
