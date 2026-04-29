package com.gorevyonetimi.service;

import com.gorevyonetimi.repository.TaskRepository;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.io.ByteArrayInputStream;
import java.util.Collections;

import static org.junit.jupiter.api.Assertions.*;

public class ReportServiceTest {

    @Test
    void generateDailyReportShouldReturnPdf() {
        TaskRepository repo = Mockito.mock(TaskRepository.class);
        Mockito.when(repo.findAll()).thenReturn(Collections.emptyList());
        ReportService reportService = new ReportService(repo);

        ByteArrayInputStream result = reportService.generateDailyReport();
        assertNotNull(result);
    }
}