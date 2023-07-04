package org.example.dto.in;

import lombok.*;

import java.time.LocalDateTime;

@Getter
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
@AllArgsConstructor
public class InMessageImplTwo extends InMessageAbstract {
    public static final String DISCRIMINATOR = "TYPE_TEST_EVENT_TWO";

    private boolean value;

    @Override
    public String getType() {
        return DISCRIMINATOR;
    }

    @Builder
    public InMessageImplTwo(String eventId, LocalDateTime occurredOn, boolean value) {
        super(eventId, occurredOn);
        this.value = value;
    }
}
