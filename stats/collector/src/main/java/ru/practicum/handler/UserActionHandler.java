package ru.practicum.handler;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.avro.specific.SpecificRecordBase;
import org.apache.kafka.clients.producer.*;
import org.apache.kafka.common.serialization.VoidSerializer;
import org.springframework.stereotype.Service;
import ru.practicum.ewm.stats.avro.UserActionAvro;
import ru.practicum.grpc.messages.UserActionProto;
import ru.practicum.mapper.UserActionAvroMapper;
import ru.practicum.serializer.CollectorSerializer;

import java.util.Properties;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;

import static ru.practicum.serializer.CollectorTopics.USER_TOPIC;

@Slf4j
@Service
@RequiredArgsConstructor
public class UserActionHandler {

    private final Producer<String, SpecificRecordBase> producer = initKafkaProducer();

    public final UserActionAvroMapper userActionAvroMapper;

    public void handle(UserActionProto userActionProto) {
        UserActionAvro userActionAvro = userActionAvroMapper.userActionToAvro(userActionProto);
        ProducerRecord<String, SpecificRecordBase> producerUserActionRecord = new ProducerRecord<>(
                USER_TOPIC,
                userActionAvro);
        Future<RecordMetadata> message = producer.send(producerUserActionRecord);
        try {
            message.get();
            log.info("Пользовательское действие типа {} успешно отправлено в топик {}", userActionAvro.getActionType().toString(), USER_TOPIC);
        } catch (InterruptedException | ExecutionException e) {
            throw new RuntimeException("Ошибка во время отправки сообщения в Kafka");
        }
    }

    private Producer<String, SpecificRecordBase> initKafkaProducer() {
        Properties kafkaConfigs = new Properties();
        kafkaConfigs.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, "localhost:9092");
        kafkaConfigs.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, VoidSerializer.class.getName());
        kafkaConfigs.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, CollectorSerializer.class.getName());
        return new KafkaProducer<>(kafkaConfigs);
    }

}
