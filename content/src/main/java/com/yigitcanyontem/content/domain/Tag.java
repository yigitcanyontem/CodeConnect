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
        name = "tag",
        uniqueConstraints = {
                @UniqueConstraint(
                        name = "tag_name_unique",
                        columnNames = "name"
                )
        }
)
@Builder
public class Tag {
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

    private String name;

    private Date createdAt;

    private Integer createdByUserId;

    @ManyToMany(mappedBy = "tags", fetch = FetchType.LAZY)
    private Set<Topic> topics;
}
