package ru.alloKafka.app.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import ru.alloKafka.app.core.KafkaAdmin;
import ru.alloKafka.app.dto.request.CreateTopicDto;

import javax.validation.Valid;
import java.util.Set;
import java.util.concurrent.ExecutionException;

@Api("Topic controller")
@RestController("topicController")
@RequestMapping("topics")
public class TopicController {

    private final KafkaAdmin kafkaAdmin;

    public TopicController(KafkaAdmin kafkaAdmin) {
        this.kafkaAdmin = kafkaAdmin;
    }

    @GetMapping
    @ApiOperation(value = "Get all topics")
    public Set<String> getAllTopics() throws ExecutionException, InterruptedException {
        return kafkaAdmin.getAllTopicsNames();
    }

    @DeleteMapping
    @ResponseStatus(value = HttpStatus.OK)
    @ApiOperation(value = "Delete topic")
    public void deleteTopic(String topicName) {
        kafkaAdmin.deleteTopic(topicName);
    }

    @PutMapping
    @ResponseStatus(value = HttpStatus.OK)
    @ApiOperation(value = "Create new topic")
    public void createTopic(@Valid CreateTopicDto dto) {
        kafkaAdmin.createTopic(
                dto.getName(), dto.getPartitionsNumber(), dto.getReplicationFactor()
        );
    }
}
