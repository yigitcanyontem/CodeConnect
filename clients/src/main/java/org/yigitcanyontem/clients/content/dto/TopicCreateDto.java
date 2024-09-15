package org.yigitcanyontem.clients.content.dto;

import lombok.*;

import java.util.Date;
import java.util.Set;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class TopicCreateDto {
    private Long id;

    private Long parentTopicId;

    private String name;

    private String description;

    private Set<Long> tags;
}
