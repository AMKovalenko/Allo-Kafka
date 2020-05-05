package ru.alloKafka.app.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.util.Set;

@Data
@Builder
@AllArgsConstructor
public class TopicDto {

    private String name;

    private Set<Integer> partitions;

}
