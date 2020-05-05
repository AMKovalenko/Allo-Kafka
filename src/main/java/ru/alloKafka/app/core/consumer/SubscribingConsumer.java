package ru.alloKafka.app.core.consumer;

import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.apache.kafka.common.annotation.InterfaceStability;

import java.util.List;
import java.util.Properties;
import java.util.concurrent.CountDownLatch;

@Slf4j
@InterfaceStability.Evolving
public class SubscribingConsumer {

    private KafkaConsumer<String, String> consumer;

    public SubscribingConsumer(String bootstrapServer) {
        Properties properties = ConsumerProperties.init(bootstrapServer);
        consumer = new KafkaConsumer<>(properties);
    }

    public void listenTo(List<String> topics) {
        CountDownLatch latch = new CountDownLatch(1);
        ConsumeLoop consumeLoop = new ConsumeLoop(consumer, latch, topics);
        Thread thread = new Thread(consumeLoop);
        thread.start();

        List<String> newMessages = consumeLoop.drawNewMessages();
        sendMessages(newMessages);

        Runtime.getRuntime().addShutdownHook(new Thread(() -> {
            consumeLoop.shutdown();
            await(latch);
        }));

        await(latch);
    }

    private void sendMessages(List<String> messagesToSend) {
        if (!messagesToSend.isEmpty()) {
            // send. to be implemented
        }
    }

    private void await(CountDownLatch latch) {
        try {
            latch.await();
        } catch (InterruptedException e) {
            log.error(e.getMessage(), e);
        }
    }
}
