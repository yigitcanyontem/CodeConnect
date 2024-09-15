package org.yigitcanyontem.clients.content.dto;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ReplyCreateDto {
    private Long topicId;

    private Long parentReplyId;

    private String content;
}
