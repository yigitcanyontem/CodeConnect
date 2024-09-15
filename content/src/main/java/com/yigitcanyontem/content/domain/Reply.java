package com.yigitcanyontem.content.domain;

import jakarta.persistence.*;
import lombok.*;

import java.util.Date;
import java.util.Set;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "reply")
@Builder
public class Reply {
    @Id
    @SequenceGenerator(
            name = "reply_id_sequence",
            sequenceName = "reply_id_sequence",
            allocationSize = 1
    )
    @GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator = "reply_id_sequence"
    )
    private Long id;

    private Long topicId;

    private Integer createdByUserId;

    private String createdByUsername;

    private Date createdAt;

    private Date updatedAt;

    private Long parentReplyId;

    private String content;

    private Long upvoteCount;

    private Long downvoteCount;
}
