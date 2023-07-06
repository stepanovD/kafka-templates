package org.example.config;

import lombok.extern.java.Log;
import org.example.dto.in.InMessageImpl;
import org.example.dto.in.InMessageImplTwo;
import org.springframework.kafka.annotation.KafkaHandler;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;

@Component
@Log
@KafkaListener(
        topics = "${spring.cloud.stream.bindings.topic-in-0.destination}",
        groupId = "${spring.cloud.stream.bindings.topic-in-0.group}",
        containerFactory = "kafkaListenerContainerFactory")
public class EventListeners {
    @KafkaHandler
    public void handleBusinessAccountCreatedEvent(@Payload InMessageImpl event) {
        System.out.println(event);
    }@KafkaHandler
    public void handleBusinessAccountCreatedEvent(@Payload InMessageImplTwo event) {
        System.out.println(event);
    }
}
