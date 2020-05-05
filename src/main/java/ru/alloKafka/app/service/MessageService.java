package ru.alloKafka.app.service;

import org.springframework.stereotype.Service;
import ru.alloKafka.app.core.Broker;
import ru.alloKafka.app.core.consumer.FetchingConsumer;
import ru.alloKafka.app.core.producer.Producer;
import ru.alloKafka.app.dto.response.MessageDto;

import java.util.List;

@Service("messageService")
public class MessageService {

    private final Broker broker;

    public MessageService(Broker broker) {
        this.broker = broker;
    }

    public void publishMessage(String message, String topic) {
        Producer producer = new Producer(broker.getBootstrapServer());
        producer.send(topic, message);
        producer.close();
    }

   /* public void listenTo(List<String> topicsToListen) {
        Consumer consumer = new Consumer(broker.getBootstrapServer());
        consumer.listenTo(topicsToListen);
    }*/

    public List<MessageDto<String>> listenTo(String topicToListen) {
        FetchingConsumer consumer = new FetchingConsumer(broker.getBootstrapServer());
        return consumer.fetchMessages(topicToListen);
    }
}
