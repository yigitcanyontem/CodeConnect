package org.yigitcanyontem.clients.report;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.yigitcanyontem.clients.report.dto.ReportCreateDto;
import org.yigitcanyontem.clients.report.dto.ReportDto;
import org.yigitcanyontem.clients.shared.dto.PaginatedResponse;

@FeignClient(
        name = "report"
)
public interface ReportClient {

    @GetMapping("/api/v1/report")
    PaginatedResponse getAllReports(@RequestHeader(HttpHeaders.AUTHORIZATION) String jwtToken,
                                    @RequestParam(name = "entityTable", required = false) String entityTable,
                                    @RequestParam(name = "entityId", required = false) Long entityId,
                                    @RequestParam(defaultValue = "0", name = "page") int page,
                                    @RequestParam(defaultValue = "10", name = "size") int size);

    @GetMapping("/api/v1/report/{id}")
    ReportDto getReport(@RequestHeader(HttpHeaders.AUTHORIZATION) String jwtToken, @PathVariable("id") Long id);

    @PostMapping("/api/v1/report")
    void createReport(@RequestHeader(HttpHeaders.AUTHORIZATION) String jwtToken, @RequestBody ReportCreateDto createDto);

    @PutMapping("{id}")
    ResponseEntity<Void> updateReportStatus(@RequestHeader(HttpHeaders.AUTHORIZATION) String jwtToken, @PathVariable("id") Long id, @RequestParam("status") String status);

}