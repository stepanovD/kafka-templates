package org.example.startup;

import lombok.extern.slf4j.Slf4j;
import org.example.dto.out.OutMessageImpl;
import org.example.dto.out.OutMessageImplTwo;
import org.springframework.boot.context.event.ApplicationStartedEvent;
import org.springframework.context.event.EventListener;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.example.services.MessageService;

import java.time.LocalDateTime;
import java.util.UUID;

@Slf4j
@Component
@Transactional
public class EventSender {
    private final MessageService messageService;

    public EventSender(MessageService messageService) {
        this.messageService = messageService;
    }

    @Async
    @EventListener(ApplicationStartedEvent.class)
    public void onApplicationStart(ApplicationStartedEvent ev) {

        String eventId = UUID.randomUUID().toString();
        String value = "val_abcd";
        OutMessageImpl event = OutMessageImpl.builder().eventId(eventId).value(value).occurredOn(LocalDateTime.now()).build();

        String eventId2 = UUID.randomUUID().toString();
        OutMessageImplTwo event2 = OutMessageImplTwo.builder().eventId(eventId2).value(true).occurredOn(LocalDateTime.now()).build();

        messageService.sendMessage(event);
        messageService.sendMessage(event2);
    }
}
