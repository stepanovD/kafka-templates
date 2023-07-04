package org.example.dto.out;

import lombok.*;

import java.time.LocalDateTime;

@Getter
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
@AllArgsConstructor
public class OutMessageImpl extends OutMessageAbstract {
    public static final String DISCRIMINATOR = "TYPE_TEST_EVENT";

    private String value;

    @Override
    public String getType() {
        return DISCRIMINATOR;
    }

    @Builder
    public OutMessageImpl(String eventId, LocalDateTime occurredOn, String value) {
        super(eventId, occurredOn);
        this.value = value;
    }
}
