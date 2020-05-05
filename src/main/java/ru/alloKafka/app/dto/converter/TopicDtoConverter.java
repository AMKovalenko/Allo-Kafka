package ru.alloKafka.app.dto.converter;

import org.apache.kafka.clients.admin.TopicDescription;
import org.apache.kafka.common.TopicPartitionInfo;
import ru.alloKafka.app.dto.response.TopicDto;

import java.util.Collection;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

public class TopicDtoConverter {

    public List<TopicDto> fromTopicDescriptions(Collection<TopicDescription> description) {
        return description.stream()
                .map(this::fromTopicDescription)
                .collect(Collectors.toList());
    }

    private TopicDto fromTopicDescription(TopicDescription description) {
        Set<Integer> partitions = description.partitions().stream()
                .map(TopicPartitionInfo::partition)
                .collect(Collectors.toSet());

        return TopicDto.builder()
                .name(description.name())
                .partitions(partitions)
                .build();
    }
}
