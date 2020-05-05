package ru.alloKafka.app.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import ru.alloKafka.app.core.Broker;
import ru.alloKafka.app.dto.request.BrokerDto;
import ru.alloKafka.app.core.KafkaAdmin;

import javax.validation.Valid;

@Api("Kafka connections controller")
@RestController("connectionController")
@RequestMapping("connections")
public class ConnectionController {

    private final Broker broker;
    private final KafkaAdmin kafkaAdmin;

    public ConnectionController(Broker broker, KafkaAdmin kafkaAdmin) {
        this.broker = broker;
        this.kafkaAdmin = kafkaAdmin;
    }

    @ResponseStatus(value = HttpStatus.OK)
    @ApiOperation(value = "Init connection with broker")
    @PutMapping("init")
    public void initConnection(@Valid BrokerDto dto) {
        broker.init(dto.getHost(), dto.getPort());
        kafkaAdmin.init(broker.getBootstrapServer());
    }

    @ResponseStatus(value = HttpStatus.OK)
    @ApiOperation(value = "Shutdown connection")
    @PutMapping("shutdown")
    public void shutdownConnection() {
        kafkaAdmin.shutdown();
    }
}
