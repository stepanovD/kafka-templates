package org.example.dto.in;

import lombok.*;

import java.time.LocalDateTime;

@Getter
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
@AllArgsConstructor
public class InMessageImpl extends InMessageAbstract {
    public static final String DISCRIMINATOR = "TYPE_TEST_EVENT";

    private String value;

    @Override
    public String getType() {
        return DISCRIMINATOR;
    }

    @Builder
    public InMessageImpl(String eventId, LocalDateTime occurredOn, String value) {
        super(eventId, occurredOn);
        this.value = value;
    }
}
