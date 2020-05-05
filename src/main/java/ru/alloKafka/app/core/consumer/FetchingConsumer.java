package ru.alloKafka.app.core.consumer;

import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import ru.alloKafka.app.dto.converter.MessageDtoConverter;
import ru.alloKafka.app.dto.response.MessageDto;

import java.time.Duration;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Properties;

@Slf4j
public class FetchingConsumer {

    public static final Duration DEFAULT_TIMEOUT_DURATION =  Duration.ofMillis(3000L);

    private KafkaConsumer<String, String> consumer;
    private MessageDtoConverter<String, String> converter;

    public FetchingConsumer(String bootstrapServer) {
        Properties properties = ConsumerProperties.init(bootstrapServer);
        consumer = new KafkaConsumer<>(properties);
        converter = new MessageDtoConverter<>();
    }

    public List<MessageDto<String>> fetchMessages(String topic) {
        List<MessageDto<String>> result = new ArrayList<>();
        ConsumerRecords<String, String> records = fetchData(topic);
        records.forEach(
                record -> result.add(converter.fromRecord(record))
        );
        return result;
    }

    private ConsumerRecords<String, String> fetchData(String topic) {
        consumer.subscribe(Collections.singletonList(topic));
        ConsumerRecords<String, String> records = consumer.poll(DEFAULT_TIMEOUT_DURATION);
        consumer.close();
        return records;
    }
}
