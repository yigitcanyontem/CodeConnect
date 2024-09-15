package com.yigitcanyontem.report.repository;

import com.yigitcanyontem.report.domain.Report;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ReportRepository extends JpaRepository<Report, Long> {
    Page<Report> findAllByEntityTableAndEntityId(String entityTable, Long entityId, Pageable pageable);
}
