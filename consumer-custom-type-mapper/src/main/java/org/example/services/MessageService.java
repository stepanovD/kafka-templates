package org.example.services;

import lombok.RequiredArgsConstructor;
import org.example.dto.out.OutMessageAbstract;
import org.springframework.cloud.stream.function.StreamBridge;
import org.springframework.integration.support.MessageBuilder;
import org.springframework.kafka.support.KafkaHeaders;
import org.springframework.messaging.Message;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class MessageService {
    private final StreamBridge streamBridge;

    public void sendMessage(OutMessageAbstract event) {
        Message<OutMessageAbstract> message = MessageBuilder
                .withPayload(event)
                .setHeader("type", event.getType())
                .setHeader("eventId", event.getEventId())
                .setHeader("occuredOn", event.getOccurredOn())
                .setHeader(KafkaHeaders.KEY, event.getKey().getBytes())
                .setHeader("__TypeId__", event.getClass().getName())
                .build();

        boolean result = streamBridge.send("consumer-test", message);

        if (!result)
            throw new RuntimeException("Can't send message.");
    }
}
