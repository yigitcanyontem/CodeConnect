package com.yigitcanyontem.report.controller;

import com.yigitcanyontem.report.service.ReportService;
import jakarta.ws.rs.core.HttpHeaders;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.yigitcanyontem.clients.auth.AuthClient;
import org.yigitcanyontem.clients.content.dto.TopicCreateDto;
import org.yigitcanyontem.clients.content.dto.TopicDto;
import org.yigitcanyontem.clients.report.dto.ReportCreateDto;
import org.yigitcanyontem.clients.report.dto.ReportDto;
import org.yigitcanyontem.clients.shared.dto.PaginatedResponse;
import org.yigitcanyontem.clients.users.dto.UsersDto;

@Slf4j
@RestController
@RequestMapping("api/v1/report")
@RequiredArgsConstructor
public class ReportController {
    private final AuthClient authClient;
    private final ReportService reportService;

    @GetMapping()
    public ResponseEntity<PaginatedResponse> getAllReports(@RequestHeader(HttpHeaders.AUTHORIZATION) String jwtToken,
                                                           @RequestParam(name = "entityTable", required = false) String entityTable,
                                                          @RequestParam(name = "entityId", required = false) Long entityId,
                                                          @RequestParam(defaultValue = "0", name = "page") int page,
                                                          @RequestParam(defaultValue = "10", name = "size") int size) {
        try {
            UsersDto user = throwIfJwtTokenIsInvalidElseReturnUser(jwtToken);
            return ResponseEntity.ok(reportService.getAllReports(entityTable, entityId, page, size, user));
        } catch (Exception e) {
            log.error("Error while gettings reports", e);
            return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("{id}")
    public ResponseEntity<ReportDto> getReport(@RequestHeader(HttpHeaders.AUTHORIZATION) String jwtToken, @PathVariable("id") Long id) {
        try {
            UsersDto user = throwIfJwtTokenIsInvalidElseReturnUser(jwtToken);
            return ResponseEntity.ok(reportService.getReport(id, user));
        } catch (Exception e) {
            log.error("Error while getting report", e);
            return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
        }
    }

    @PutMapping("{id}")
    public ResponseEntity<Void> updateReportStatus(@RequestHeader(HttpHeaders.AUTHORIZATION) String jwtToken, @PathVariable("id") Long id, @RequestParam("status") String status) {
        try {
            UsersDto user = throwIfJwtTokenIsInvalidElseReturnUser(jwtToken);
            reportService.updateStatus(id, status, user);
            return ResponseEntity.ok().build();
        } catch (Exception e) {
            log.error("Error while updating report status", e);
            return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
        }
    }

    @PostMapping()
    public ResponseEntity<Void> createTopic(@RequestHeader(HttpHeaders.AUTHORIZATION) String jwtToken, @RequestBody ReportCreateDto createDto) {
        try {
            UsersDto user = throwIfJwtTokenIsInvalidElseReturnUser(jwtToken);
            reportService.save(createDto, user);
            return ResponseEntity.ok().build();
        } catch (Exception e) {
            log.error("Error while creating report", e);
            return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
        }
    }


    public UsersDto throwIfJwtTokenIsInvalidElseReturnUser(String jwtToken) {
        UsersDto user = authClient.validateToken(jwtToken).getBody();

        if (user == null) {
            throw new IllegalArgumentException("Invalid JWT token");
        }

        return user;
    }
}
