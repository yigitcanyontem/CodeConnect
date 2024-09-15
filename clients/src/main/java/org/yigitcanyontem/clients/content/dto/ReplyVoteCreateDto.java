package org.yigitcanyontem.clients.content.dto;

import lombok.*;
import org.yigitcanyontem.clients.content.enums.VoteType;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ReplyVoteCreateDto {
    private Long replyId;

    private VoteType voteType;
}
