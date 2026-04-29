package com.gorevyonetimi.controller;

import com.gorevyonetimi.model.Task;
import com.gorevyonetimi.service.PdfService;
import com.gorevyonetimi.service.TaskService;
import jakarta.servlet.ServletOutputStream;
import jakarta.servlet.http.HttpServletResponse;
import org.junit.jupiter.api.Test;
import org.springframework.mock.web.DelegatingServletOutputStream;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.util.Collections;
import java.util.List;

import static org.mockito.Mockito.*;

public class PdfControllerTest {

    @Test
    public void testGeneratePdf() throws IOException {
        PdfService pdfService = mock(PdfService.class);
        TaskService taskService = mock(TaskService.class);
        HttpServletResponse response = mock(HttpServletResponse.class);

        List<Task> dummyTasks = Collections.singletonList(new Task());
        ByteArrayInputStream pdfStream = new ByteArrayInputStream("PDF test içeriği".getBytes());

        when(taskService.getAllTasks()).thenReturn(dummyTasks);
        when(pdfService.generateTaskListPdf(dummyTasks)).thenReturn(pdfStream);

        ServletOutputStream outputStream = new DelegatingServletOutputStream(System.out);
        when(response.getOutputStream()).thenReturn(outputStream);

        PdfController controller = new PdfController(pdfService, taskService);
        controller.generatePdf(response);

        verify(response).setContentType("application/pdf");
        verify(response).setHeader("Content-Disposition", "attachment; filename=task-listesi.pdf");
        verify(taskService).getAllTasks();
        verify(pdfService).generateTaskListPdf(dummyTasks);
        verify(response).getOutputStream();
    }
}
