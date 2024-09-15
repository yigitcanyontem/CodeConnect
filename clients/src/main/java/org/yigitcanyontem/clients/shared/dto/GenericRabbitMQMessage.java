package org.yigitcanyontem.clients.shared.dto;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class GenericRabbitMQMessage {
    private String endpoint;
    private Object message;
}
