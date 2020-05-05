package ru.alloKafka.app.core;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Component;
import ru.alloKafka.app.utils.StringUtils;

@Data
@NoArgsConstructor
@Component
public class Broker {

    private String bootstrapServer = "localhost:9092";

    public void init(String host, String port) {
        bootstrapServer = host
                .concat(StringUtils.COLON)
                .concat(port);
    }
}
