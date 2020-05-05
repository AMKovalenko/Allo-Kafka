package ru.alloKafka.app.dto.converter;

import org.apache.kafka.clients.consumer.ConsumerRecord;
import ru.alloKafka.app.dto.response.MessageDto;

public class MessageDtoConverter<K, V> {

    public MessageDto<V> fromRecord(ConsumerRecord<K, V> record) {
        MessageDto<V> dto = new MessageDto<>();
        dto.setPayload(record.value());
        dto.setOffset(record.offset());
        dto.setPartition(record.partition());
        return dto;
    }
}
