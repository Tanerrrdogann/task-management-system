package com.gorevyonetimi.controller;

import com.gorevyonetimi.service.PdfReportService;
import com.gorevyonetimi.service.TaskService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.Collections;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

public class ReportControllerTest {

    private MockMvc mockMvc;
    private PdfReportService pdfReportService;
    private TaskService taskService;

    @BeforeEach
    public void setUp() {
        pdfReportService = mock(PdfReportService.class);
        taskService = mock(TaskService.class);

        ReportController reportController = new ReportController(pdfReportService, taskService);
        mockMvc = MockMvcBuilders.standaloneSetup(reportController).build();
    }

    @Test
    public void testGeneratePdfReport() throws Exception {
        when(taskService.getAllTasks()).thenReturn(Collections.emptyList());

        mockMvc.perform(get("/api/report/pdf")
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().string("PDF raporu oluşturuldu: reports/task_report.pdf"));

        verify(taskService, times(1)).getAllTasks();
        verify(pdfReportService, times(1)).generateTaskReport(Collections.emptyList());
    }
}
