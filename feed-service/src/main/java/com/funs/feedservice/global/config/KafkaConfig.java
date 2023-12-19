package com.funs.feedservice.global.config;

import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.apache.kafka.common.serialization.StringSerializer;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.annotation.EnableKafka;
import org.springframework.kafka.core.*;

import java.util.HashMap;
import java.util.Map;

@EnableKafka
@Configuration
public class KafkaConfig {

//    public KafkaConfig(@Value("${kafka.host}") String kafkaServerHost,
//                       @Value("${kafka.port}")String kafkaServerPort) {
//        this.kafkaServerHost = kafkaServerHost;
//        this.kafkaServerPort = kafkaServerPort;
//    }
//    private final String kafkaServerHost;
//    private final String kafkaServerPort;
public KafkaConfig(@Value("${kafka.bootstrap.servers}") String bootstrapServers) {
    this.bootstrapServers = bootstrapServers;
}

    private final String bootstrapServers;

    @Bean
    public ProducerFactory<String, String> producerFactory(){
        Map<String, Object> properties = new HashMap<>();
        properties.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, "172.18.0.151:9092,172.18.0.152:9093,172.18.0.153:9094");
        properties.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class);
        properties.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, StringSerializer.class);
//        properties.put(ProducerConfig.DELIVERY_TIMEOUT_MS_CONFIG, 5000);

        return new DefaultKafkaProducerFactory<>(properties);
    }

    @Bean
    public KafkaTemplate<String, String> kafkaTemplate(){
        return new KafkaTemplate<>(producerFactory());
    }
}
