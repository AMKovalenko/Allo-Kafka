package ru.alloKafka.app.dto.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PublishMessageDto {

    @NotBlank
    private String topic;

    @NotBlank
    private String message;
}
