package com.wizard.btrs.controller;

import com.wizard.btrs.dto.ReconciliationSummary;
import com.wizard.btrs.service.ReportService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class ReportController {

    private final ReportService reportService;

    @GetMapping("/reports/summary")
    public ReconciliationSummary getSummary() {

        return reportService.getSummary();
    }
}