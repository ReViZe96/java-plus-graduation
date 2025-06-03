package ru.practicum.configuration;

import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import ru.practicum.ewm.stats.avro.EventSimilarityAvro;
import ru.practicum.ewm.stats.avro.UserActionAvro;

import java.util.Properties;

@Configuration
public class KafkaConfiguration {

    @Value("${analyzer.kafka.bootstrap-servers}")
    private String bootstrapServers;

    @Value("${analyzer.kafka.event-similarity-consumer.client-id}")
    private String eventSimilarityClientId;

    @Value("${analyzer.kafka.event-similarity-consumer.group-id}")
    private String eventSimilarityGroupId;

    @Value("${analyzer.kafka.event-similarity-consumer.value-deserializer}")
    private String eventSimilarityValueDeserializer;

    @Value("${analyzer.kafka.user-actions-consumer.client-id}")
    private String userActionClientId;

    @Value("${analyzer.kafka.user-actions-consumer.group-id}")
    private String userActionGroupId;

    @Value("${analyzer.kafka.user-actions-consumer.value-deserializer}")
    private String userActionValueDeserializer;

    @Value("${analyzer.kafka.key-deserializer}")
    private String keyDeserializer;

    @Bean
    public KafkaConsumer<String, EventSimilarityAvro> eventSimilarityConsumer() {
        Properties snapshotsCounsumerConfigs = new Properties();
        snapshotsCounsumerConfigs.put(ConsumerConfig.CLIENT_ID_CONFIG, eventSimilarityClientId);
        snapshotsCounsumerConfigs.put(ConsumerConfig.GROUP_ID_CONFIG, eventSimilarityGroupId);
        snapshotsCounsumerConfigs.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, bootstrapServers);
        snapshotsCounsumerConfigs.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, keyDeserializer);
        snapshotsCounsumerConfigs.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, eventSimilarityValueDeserializer);
        return new KafkaConsumer<>(snapshotsCounsumerConfigs);
    }

    @Bean
    public KafkaConsumer<String, UserActionAvro> userActionConsumer() {
        Properties hubsConsumerConfigs = new Properties();
        hubsConsumerConfigs.put(ConsumerConfig.CLIENT_ID_CONFIG, userActionClientId);
        hubsConsumerConfigs.put(ConsumerConfig.GROUP_ID_CONFIG, userActionGroupId);
        hubsConsumerConfigs.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, bootstrapServers);
        hubsConsumerConfigs.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, keyDeserializer);
        hubsConsumerConfigs.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, userActionValueDeserializer);
        return new KafkaConsumer<>(hubsConsumerConfigs);
    }

}
