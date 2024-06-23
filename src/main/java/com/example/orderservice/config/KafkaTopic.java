package com.example.orderservice.config;

import com.example.orderservice.entity.Order;
import org.apache.kafka.clients.admin.NewTopic;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.common.serialization.StringSerializer;
//import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.config.TopicBuilder;
import org.springframework.kafka.core.DefaultKafkaProducerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.core.ProducerFactory;
import org.springframework.kafka.support.serializer.JsonSerializer;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;
import java.util.Map;

@Configuration
public class KafkaTopic  {

    public NewTopic paymenttopic(){
        return TopicBuilder.name("ORDER_PAYMENT_TOPIC")
                .partitions(3)
                .replicas(1)
                .build();
    }


//    @Bean
//    public ProducerFactory<String, Order> producerFactory()
//    {
//
//        // Creating a Map
//        Map<String, Object> config = new HashMap<>();
//
//        // Adding Configuration
//
//        // 127.0.0.1:9092 is the default port number for
//        // kafka
//        config.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG,
//                "localhost:9092");
//        config.put(
//                ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG,
//                StringSerializer.class);
//        config.put(
//                ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG,
//                JsonSerializer.class);
//
//        return new DefaultKafkaProducerFactory<>(config);
//    }
//
//    @Bean
//    public KafkaTemplate kafkaTemplate()
//    {
//
//        return new KafkaTemplate<>(producerFactory());
//    }

    @Bean
    public RestTemplate restTemplate(){
        return new RestTemplate();
    }
}
