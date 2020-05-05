package ru.alloKafka.app.core.producer;

import org.apache.kafka.clients.producer.Callback;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerRecord;
import ru.alloKafka.app.exception.MessageSendingFailedException;

import java.util.Properties;

public class Producer {

    final KafkaProducer<String, String> producer;

    public Producer(String serverName) {
        Properties properties = ProducerProperties.init(serverName);
        producer = new KafkaProducer<>(properties);
    }

    public void send(String topic, String message) {
        ProducerRecord<String, String> producerRecord = new ProducerRecord<>(topic, message);
        Callback callback = (metadata, exception) -> {
            if (exception != null) {
                throw new MessageSendingFailedException(message, topic, exception);
            }
        };
        producer.send(producerRecord, callback);
    }

    public void close() {
        producer.close();
    }
}
