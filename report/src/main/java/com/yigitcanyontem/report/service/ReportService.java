package com.yigitcanyontem.report.service;

import com.yigitcanyontem.report.domain.Report;
import com.yigitcanyontem.report.repository.ReportRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.yigitcanyontem.clients.report.dto.ReportCreateDto;
import org.yigitcanyontem.clients.report.dto.ReportDto;
import org.yigitcanyontem.clients.report.dto.ReportStatus;
import org.yigitcanyontem.clients.shared.dto.PaginatedResponse;
import org.yigitcanyontem.clients.users.UsersClient;
import org.yigitcanyontem.clients.users.dto.UsersDto;
import org.yigitcanyontem.clients.users.enums.Role;

import java.util.Date;
import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor
public class ReportService {
    private final ReportRepository reportRepository;
    private final UsersClient usersClient;

    public PaginatedResponse getAllReports(String entityTable, Long entityId, int page, int size, UsersDto user) {
        Sort sort = Sort.by(Sort.Order.desc("createdAt"));
        PageRequest pageRequest = PageRequest.of(page, size, sort);
        Page<Report> reportPage;

        if (entityTable != null && entityId != null) {
            reportPage = reportRepository.findAllByEntityTableAndEntityId(entityTable, entityId, pageRequest);
        } else {
            reportPage = reportRepository.findAll(pageRequest);
        }

        List<ReportDto> reportDtos = reportPage.getContent().stream().parallel()
                .map(this::convertToDto)
                .toList();

        return PaginatedResponse.builder()
                .data(reportDtos)
                .page(reportPage.getNumber())
                .size(reportPage.getSize())
                .totalElements(reportPage.getTotalElements())
                .totalPages(reportPage.getTotalPages())
                .build();
    }

    public ReportDto getReport(Long id, UsersDto user) {
        if (!user.getRole().equals(Role.ADMIN)) {
            throw new RuntimeException("Only admins can update report status");
        }

        Report report = reportRepository.findById(id).orElseThrow(
                () -> new RuntimeException("Report not found")
        );
        return convertToDto(report);
    }

    public void updateStatus(Long id, String  status, UsersDto user) {
        if (!user.getRole().equals(Role.ADMIN)) {
            throw new RuntimeException("Only admins can update report status");
        }

        Report report = reportRepository.findById(id).orElseThrow(() -> new RuntimeException("Report not found"));
        report.setStatus(ReportStatus.valueOf(status));
        reportRepository.saveAndFlush(report);
    }

    public void save(ReportCreateDto createDto, UsersDto user) {
        Report report = Report.builder()
                .title(createDto.getTitle())
                .content(createDto.getContent())
                .status(ReportStatus.PENDING)
                .entityTable(createDto.getEntityTable())
                .entityId(createDto.getEntityId())
                .userId(user.getId())
                .createdAt(new Date())
                .build();

        reportRepository.saveAndFlush(report);
    }

    private ReportDto convertToDto(Report report) {
        return ReportDto.builder()
                .id(report.getId())
                .title(report.getTitle())
                .content(report.getContent())
                .status(report.getStatus())
                .entityTable(report.getEntityTable())
                .entityId(report.getEntityId())
                .userId(usersClient.getUserById(report.getUserId()).getBody())
                .createdAt(report.getCreatedAt())
                .build();
    }
}
