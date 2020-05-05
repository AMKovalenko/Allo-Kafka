package ru.alloKafka.app.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import ru.alloKafka.app.dto.request.PublishMessageDto;
import ru.alloKafka.app.dto.request.TopicDto;
import ru.alloKafka.app.dto.response.MessageDto;
import ru.alloKafka.app.service.MessageService;

import javax.validation.Valid;
import java.util.List;

@Api("Kafka Message Controller")
@RestController("messageController")
@RequestMapping("/messages")
public class MessageController {

    private final MessageService messageService;

    public MessageController(MessageService messageService) {
        this.messageService = messageService;
    }

    @ResponseStatus(value = HttpStatus.OK)
    @ApiOperation(value = "Publish message")
    @PutMapping
    public void publishMessage(@Valid PublishMessageDto dto) {
        messageService.publishMessage(dto.getMessage(), dto.getTopic());
    }

    @ApiOperation(value = "Fetch new messages")
    @GetMapping
    public List<MessageDto<String>> getMessages(TopicDto topic) {
        return messageService.listenTo(topic.getName());
    }
}
