package com.yigitcanyontem.content.domain;

import jakarta.persistence.*;
import lombok.*;
import org.yigitcanyontem.clients.content.enums.VoteType;

import java.util.Date;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(
        name = "reply_vote",
        uniqueConstraints = {
                @UniqueConstraint(
                        name = "user_reply_unique",
                        columnNames = {"user_id", "reply_id"}
                )
        }
)
@Builder
public class ReplyVote {
    @Id
    @SequenceGenerator(
            name = "reply_vote_id_sequence",
            sequenceName = "reply_vote_id_sequence",
            allocationSize = 1
    )
    @GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator = "reply_vote_id_sequence"
    )
    private Long id;

    @Column(name = "user_id", nullable = false)
    private Long userId;

    @Column(name = "reply_id", nullable = false)
    private Long replyId;

    @Enumerated(EnumType.STRING)
    @Column(name = "vote_type", nullable = false)
    private VoteType voteType;

    private Date createdAt;
}
