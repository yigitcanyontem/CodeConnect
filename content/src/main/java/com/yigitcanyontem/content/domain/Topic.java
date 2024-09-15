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
@Table(
        name = "topic",
        uniqueConstraints = {
                @UniqueConstraint(
                        name = "topic_name_unique",
                        columnNames = "name"
                )
        }
)
@Builder
public class Topic {
    @Id
    @SequenceGenerator(
            name = "topic_id_sequence",
            sequenceName = "topic_id_sequence",
            allocationSize = 1
    )
    @GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator = "topic_id_sequence"
    )
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

    @ManyToMany
    @JoinTable(
            name = "topic_tag",
            joinColumns = @JoinColumn(name = "topic_id"),
            inverseJoinColumns = @JoinColumn(name = "tag_id")
    )
    private Set<Tag> tags;
}
