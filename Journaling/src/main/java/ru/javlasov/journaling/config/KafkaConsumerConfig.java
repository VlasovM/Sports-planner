package ru.javlasov.journaling.config;

import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.annotation.EnableKafka;
import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory;
import org.springframework.kafka.core.ConsumerFactory;
import org.springframework.kafka.core.DefaultKafkaConsumerFactory;
import org.springframework.kafka.support.serializer.JsonDeserializer;
import ru.javlasov.journaling.dto.LoggerEvent;

import java.util.HashMap;

@EnableKafka
@Configuration
public class KafkaConsumerConfig {

    @Value(value = "${spring.kafka.consumer.bootstrap-servers}")
    private String addresses;

    @Value(value = "${spring.kafka.consumer.group-id}")
    private String groupId;

    @Bean
    public ConsumerFactory<String, LoggerEvent> consumerFactory() {
        JsonDeserializer<LoggerEvent> payloadJsonDeserializer = new JsonDeserializer<>(LoggerEvent.class, false);
        payloadJsonDeserializer.trustedPackages("ru.javlasov.*");
        var config = new HashMap<String, Object>();
        config.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, addresses);
        config.put(ConsumerConfig.GROUP_ID_CONFIG, groupId);
        config.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class);
        config.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, JsonDeserializer.class);
        var kafkaConsumerFactory = new DefaultKafkaConsumerFactory<Long, LoggerEvent>(config);
        kafkaConsumerFactory.setValueDeserializer(payloadJsonDeserializer);
        return new DefaultKafkaConsumerFactory<>(config, new StringDeserializer(),
                payloadJsonDeserializer);
    }

    @Bean
    public ConcurrentKafkaListenerContainerFactory<String, LoggerEvent> kafkaListenerContainerFactory(
            ConsumerFactory<String, LoggerEvent> consumerFactory) {
        ConcurrentKafkaListenerContainerFactory<String, LoggerEvent> concurrentKafkaListenerContainerFactory
                = new ConcurrentKafkaListenerContainerFactory<>();
        concurrentKafkaListenerContainerFactory.setConsumerFactory(consumerFactory);
        return concurrentKafkaListenerContainerFactory;
    }

}
