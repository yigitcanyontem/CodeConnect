package org.yigitcanyontem.clients.content.dto;

import lombok.*;
import org.yigitcanyontem.clients.users.dto.UsersDto;

import java.util.Date;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ReplyDto {
    private Long id;

    private Long topicId;

    private TopicDto topic;

    private Integer createdByUserId;

    private String createdByUsername;

    private Date createdAt;

    private Date updatedAt;

    private Long parentReplyId;

    private String content;

    private Long upvoteCount;

    private Long downvoteCount;

    private Long childReplyCount;
}
