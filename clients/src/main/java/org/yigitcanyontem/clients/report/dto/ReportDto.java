package org.yigitcanyontem.clients.report.dto;

import lombok.*;
import org.yigitcanyontem.clients.users.dto.UsersDto;

import java.util.Date;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ReportDto {
    private Long id;

    private String title;

    private String content;

    private ReportStatus status;

    private String entityTable;

    private Long entityId;

    private UsersDto userId;

    private Date createdAt;
}
