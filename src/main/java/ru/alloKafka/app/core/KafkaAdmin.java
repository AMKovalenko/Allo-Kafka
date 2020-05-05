package ru.alloKafka.app.core;

import lombok.NoArgsConstructor;
import org.apache.kafka.clients.admin.AdminClient;
import org.apache.kafka.clients.admin.DescribeTopicsResult;
import org.apache.kafka.clients.admin.ListTopicsResult;
import org.apache.kafka.clients.admin.NewTopic;
import org.springframework.stereotype.Component;
import ru.alloKafka.app.core.consumer.ConsumerProperties;
import ru.alloKafka.app.dto.converter.TopicDtoConverter;
import ru.alloKafka.app.dto.response.TopicDto;

import java.util.Collections;
import java.util.List;
import java.util.Properties;
import java.util.Set;
import java.util.concurrent.ExecutionException;

@Component
@NoArgsConstructor
public class KafkaAdmin {

    private AdminClient kafkaAdminClient;

    public void init(String bootstrapServer) {
        Properties properties = ConsumerProperties.init(bootstrapServer);
        kafkaAdminClient = AdminClient.create(properties);
    }

    public Set<String> getAllTopicsNames() throws ExecutionException, InterruptedException {
        ListTopicsResult listTopicsResult = kafkaAdminClient.listTopics();
        return listTopicsResult.names().get();
    }

    public List<TopicDto> getTopicsInfo(List<String> topics) throws ExecutionException, InterruptedException {
        DescribeTopicsResult descriptions = kafkaAdminClient.describeTopics(topics);
        TopicDtoConverter converter = new TopicDtoConverter();
        return descriptions.all().thenApply(
                topicInfo -> converter.fromTopicDescriptions(topicInfo.values())
        ).get();
    }

    public void deleteTopic(String topicName) {
        kafkaAdminClient.deleteTopics(Collections.singletonList(topicName));
    }

    public void createTopic(String topicName, Integer partitionsNumber, String replicationFactor) {
        NewTopic newTopic = new NewTopic(topicName, partitionsNumber, Short.parseShort(replicationFactor));
        kafkaAdminClient.createTopics(Collections.singletonList(newTopic));
    }

    public void shutdown() {
        kafkaAdminClient.close();
    }
}
