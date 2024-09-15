package org.yigitcanyontem.clients.content.dto;

import lombok.*;
import org.yigitcanyontem.clients.content.enums.VoteType;

import java.util.Date;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ReplyVoteDto {
    private Long id;

    private Integer userId;

    private Long replyId;

    private VoteType voteType;

    private Date createdAt;
}
