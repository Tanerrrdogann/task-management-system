package com.gorevyonetimi.controller;

import com.gorevyonetimi.service.PdfReportService;
import com.gorevyonetimi.service.TaskService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/report")
public class ReportController {

    private final PdfReportService pdfReportService;
    private final TaskService taskService;

    public ReportController(PdfReportService pdfReportService, TaskService taskService) {
        this.pdfReportService = pdfReportService;
        this.taskService = taskService;
    }

    @GetMapping("/pdf")
    public ResponseEntity<String> generatePdfReport() {
        pdfReportService.generateTaskReport(taskService.getAllTasks());
        return ResponseEntity.ok("PDF raporu oluşturuldu: reports/task_report.pdf");
    }
}
