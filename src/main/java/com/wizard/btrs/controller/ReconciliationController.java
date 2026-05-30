package com.wizard.btrs.controller;

import com.wizard.btrs.entity.ReconciledTransaction;
import com.wizard.btrs.service.ReconciliationService;
import lombok.RequiredArgsConstructor;
import org.springframework.batch.core.job.Job;
import org.springframework.batch.core.job.parameters.JobParameters;
import org.springframework.batch.core.job.parameters.JobParametersBuilder;
import org.springframework.batch.core.launch.JobOperator;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/reconciliation")
public class ReconciliationController {

    private final ReconciliationService reconciliationService;
    private final JobOperator jobOperator;
    private final Job reconciliationJob;

    @PostMapping("/run")
    public String runJob() {

        try {

            JobParameters jobParameters =
                    new JobParametersBuilder()
                            .addLong(
                                    "startAt",
                                    System.currentTimeMillis()
                            )
                            .toJobParameters();

            jobOperator.start(
                    reconciliationJob,
                    jobParameters
            );

            return "Job Started Successfully";

        } catch (Exception e) {

            return "Job Failed : " + e.getMessage();
        }
    }

    @GetMapping("/results")
    public List<ReconciledTransaction> getAllResults() {

        return reconciliationService.getAllResults();
    }
}