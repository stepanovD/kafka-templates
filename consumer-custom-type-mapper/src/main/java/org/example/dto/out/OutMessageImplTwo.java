package org.example.dto.out;

import lombok.*;

import java.time.LocalDateTime;

@Getter
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
@AllArgsConstructor
public class OutMessageImplTwo extends OutMessageAbstract {
    public static final String DISCRIMINATOR = "TYPE_TEST_EVENT_TWO";

    private boolean value;

    @Override
    public String getType() {
        return DISCRIMINATOR;
    }

    @Builder
    public OutMessageImplTwo(String eventId, LocalDateTime occurredOn, boolean value) {
        super(eventId, occurredOn);
        this.value = value;
    }
}
