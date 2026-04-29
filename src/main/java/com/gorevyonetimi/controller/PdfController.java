package com.gorevyonetimi.controller;

import com.gorevyonetimi.service.PdfService;
import com.gorevyonetimi.service.TaskService;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;

import java.io.IOException;

@Controller
public class PdfController {

    private final PdfService pdfService;
    private final TaskService taskService;

    public PdfController(PdfService pdfService, TaskService taskService) {
        this.pdfService = pdfService;
        this.taskService = taskService;
    }

    @PostMapping("/generate-pdf")
    public void generatePdf(HttpServletResponse response) throws IOException {
        response.setContentType("application/pdf");
        response.setHeader("Content-Disposition", "attachment; filename=task-listesi.pdf");

        var tasks = taskService.getAllTasks();
        var pdfStream = pdfService.generateTaskListPdf(tasks);

        pdfStream.transferTo(response.getOutputStream());
    }
}
