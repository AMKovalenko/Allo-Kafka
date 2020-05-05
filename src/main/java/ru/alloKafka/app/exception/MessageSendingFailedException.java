package ru.alloKafka.app.exception;

public class MessageSendingFailedException extends RuntimeException {

    public static final String MESSAGE_PATTERN = "Message '%s' sending to the topic '%s' failed due to '%s'.";

    public MessageSendingFailedException(String failedMessage, String topic, Exception exception) {
        super(String.format(MESSAGE_PATTERN, failedMessage, topic, exception.getMessage()));
    }
}
