package org.yigitcanyontem.clients.report.dto;

import lombok.*;

import java.util.Date;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ReportCreateDto {
    private String title;

    private String content;

    private String entityTable;

    private Long entityId;
}
