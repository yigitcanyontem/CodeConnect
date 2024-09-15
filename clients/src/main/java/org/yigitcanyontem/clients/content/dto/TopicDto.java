package org.yigitcanyontem.clients.content.dto;

import lombok.*;

import java.util.Date;
import java.util.Set;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class TopicDto {
    private Long id;

    private Long parentTopicId;

    private String name;

    private String description;

    private Integer createdByUserId;

    private String createdByUsername;

    private Integer updatedByUserId;

    private String updatedByUsername;

    private Date createdAt;

    private Long viewCountTotal;

    private Long viewCountLastWeek;

    private String slug;

    private Set<TagDto> tags;

    private Long replyCount;

    private Long childTopicCount;
}
