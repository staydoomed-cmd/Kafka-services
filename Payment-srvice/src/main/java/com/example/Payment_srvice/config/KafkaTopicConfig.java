package com.example.Payment_srvice.config;


import org.apache.kafka.clients.admin.NewTopic;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.config.TopicBuilder;

@Configuration
public class KafkaTopicConfig {

    @Value("${spring.kafka.topic.new-orders.partitions:3}")
    private int partitions;

    @Bean
    public NewTopic newOrdersTopic() {
        return TopicBuilder.name("new_orders")
                .partitions(partitions)
                .replicas(1)
                .build();
    }
}