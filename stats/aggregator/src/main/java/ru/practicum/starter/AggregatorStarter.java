package ru.practicum.starter;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.avro.specific.SpecificRecordBase;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.clients.producer.RecordMetadata;
import org.apache.kafka.common.errors.WakeupException;
import org.springframework.stereotype.Component;
import ru.practicum.ewm.stats.avro.EventSimilarityAvro;
import ru.practicum.ewm.stats.avro.UserActionAvro;
import ru.practicum.service.AggregatorService;

import java.time.Duration;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;

import static ru.practicum.serializer.AggregatorTopics.EVENT_SIMILARITY;
import static ru.practicum.serializer.AggregatorTopics.USER_TOPIC;

@Slf4j
@Component
@RequiredArgsConstructor
public class AggregatorStarter {

    private final AggregatorService aggregatorService;
    private final KafkaProducer<String, SpecificRecordBase> producer;
    private final KafkaConsumer<String, SpecificRecordBase> consumer;

    /**
     *
     */
    public void start() {
        try {
            consumer.subscribe(List.of(USER_TOPIC));
            Runtime.getRuntime().addShutdownHook(new Thread(consumer::wakeup));
            log.info("Агрегатор подписался на топик " + USER_TOPIC);

            while (true) {
                ConsumerRecords<String, SpecificRecordBase> records = consumer.poll(Duration.ofMillis(5000));
                log.info("Получены " + records.count() + " записей о действиях пользователей из топика " + USER_TOPIC);
                for (ConsumerRecord<String, SpecificRecordBase> record : records) {
                    UserActionAvro userAction = (UserActionAvro) record.value();
                    //основная логика работы агрегатора
                    List<EventSimilarityAvro> eventSimilarities = aggregatorService.createEventSimilarityMessages(userAction);
                    log.info("Сообщения о сходствах мероприятий сформированы");
                    for (EventSimilarityAvro eventSimilarity : eventSimilarities) {
                        sendToKafka(EVENT_SIMILARITY, "" + eventSimilarity.getEventA(), eventSimilarity);
                    }
                }
                consumer.commitSync();
            }

        } catch (WakeupException ignored) {
            // игнорируем - закрываем консьюмер и продюсер в блоке finally
        } catch (Exception e) {
            log.error("Произошла ошибка при формировании сообщения о сходстве двух мероприятий. \n {} : \n {}", e.getMessage(),
                    e.getStackTrace());
        } finally {
            try {
                producer.flush();
                consumer.commitSync();
            } finally {
                consumer.close();
                producer.close();
            }
        }
    }

    public void sendToKafka(String topicName, String eventId, SpecificRecordBase eventSimilarity) {
        ProducerRecord<String, SpecificRecordBase> producerEventSimilarityRecord = new ProducerRecord<>(
                topicName,
                null,
                System.currentTimeMillis(),
                eventId,
                eventSimilarity);
        Future<RecordMetadata> message = producer.send(producerEventSimilarityRecord);
        try {
            message.get();
            log.info("Сообщение о сходстве двух мероприятий отправлено в топик {}", topicName);
        } catch (InterruptedException | ExecutionException e) {
            throw new RuntimeException("Ошибка во время отправки сообщения в Kafka");
        }
    }

}
