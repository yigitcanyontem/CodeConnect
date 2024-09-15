package com.yigitcanyontem.report.domain;

import jakarta.persistence.*;
import lombok.*;
import org.yigitcanyontem.clients.report.dto.ReportStatus;

import java.util.Date;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "report")
@Builder
public class Report {
    @Id
    @SequenceGenerator(
            name = "report_id_sequence",
            sequenceName = "report_id_sequence",
            allocationSize = 1
    )
    @GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator = "report_id_sequence"
    )
    private Long id;

    private String title;

    private String content;

    @Enumerated(EnumType.STRING)
    private ReportStatus status;

    private String entityTable;

    private Long entityId;

    private Integer userId;

    private Date createdAt;
}
