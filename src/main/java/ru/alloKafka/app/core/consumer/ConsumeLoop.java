package ru.alloKafka.app.core.consumer;

import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.apache.kafka.common.errors.WakeupException;

import java.time.Duration;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.CountDownLatch;

@Slf4j
public class ConsumeLoop implements Runnable {

    private CountDownLatch latch;
    private KafkaConsumer<String, String> consumer;
    private List<String> topics;
    private List<String> messageStorage;

    public ConsumeLoop(KafkaConsumer<String, String> consumer, CountDownLatch latch, List<String> topics) {
        this.consumer = consumer;
        this.latch = latch;
        this.topics = topics;
        messageStorage = Collections.synchronizedList(new ArrayList<>());
    }

    public void run() {
        try {
            consumer.subscribe(topics);
            while (true) {
                ConsumerRecords<String, String> records = consumer.poll(Duration.ofMillis(200));
                records.forEach(record -> messageStorage.add(record.value()));
            }
        } catch (WakeupException we) {
            // do nothing, just close connection.
        } finally {
            consumer.close();
            latch.countDown();
        }
    }

    public synchronized List<String> drawNewMessages() {
        List<String> result = new ArrayList<>(messageStorage);
        messageStorage.clear();
        return result;
    }

    public void shutdown() {
        consumer.wakeup();
    }
}
