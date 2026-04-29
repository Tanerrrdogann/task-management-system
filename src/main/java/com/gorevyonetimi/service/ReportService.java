package com.gorevyonetimi.service;

import com.gorevyonetimi.model.Task;
import com.gorevyonetimi.repository.TaskRepository;
import com.gorevyonetimi.util.FileLogger;
import com.lowagie.text.*;
import com.lowagie.text.pdf.PdfWriter;
import org.springframework.stereotype.Service;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Service
public class ReportService {

    private final TaskRepository taskRepository;

    public ReportService(TaskRepository taskRepository) {
        this.taskRepository = taskRepository;
    }

    public ByteArrayInputStream generateDailyReport() {
        List<Task> tasks = taskRepository.findAll();
        FileLogger.log("Günlük rapor hazırlanıyor. Görev sayısı: " + tasks.size());
        return buildReport(tasks, "Günlük Görev Performans Raporu");
    }

    public ByteArrayInputStream generateWeeklyReport() {
        LocalDateTime oneWeekAgo = LocalDateTime.now().minusDays(7);
        List<Task> tasks = taskRepository.findAll().stream()
                .filter(task -> task.getCreatedAt() != null && task.getCreatedAt().isAfter(oneWeekAgo))
                .toList();

        FileLogger.log("Haftalık rapor hazırlanıyor. Son 7 gün içindeki görev sayısı: " + tasks.size());
        return buildReport(tasks, "Haftalık Görev Performans Raporu");
    }

    private ByteArrayInputStream buildReport(List<Task> tasks, String titleText) {
        long completed = tasks.stream().filter(Task::isCompleted).count();
        long pending = tasks.size() - completed;

        try (ByteArrayOutputStream out = new ByteArrayOutputStream()) {
            Document document = new Document();
            PdfWriter.getInstance(document, out);
            document.open();

            Font headerFont = new Font(Font.HELVETICA, 18, Font.BOLD);
            Paragraph title = new Paragraph(titleText, headerFont);
            title.setAlignment(Element.ALIGN_CENTER);
            title.setSpacingAfter(20);
            document.add(title);

            Font bodyFont = new Font(Font.HELVETICA, 12);
            document.add(new Paragraph("Tarih: " + LocalDate.now(), bodyFont));
            document.add(new Paragraph("Toplam Görev: " + tasks.size(), bodyFont));
            document.add(new Paragraph("Tamamlanan Görev: " + completed, bodyFont));
            document.add(new Paragraph("Bekleyen Görev: " + pending, bodyFont));

            document.close();
            FileLogger.log(titleText + " başarıyla oluşturuldu. Tamamlanan: " + completed + ", Bekleyen: " + pending);
            return new ByteArrayInputStream(out.toByteArray());
        } catch (Exception e) {
            FileLogger.log("Rapor oluşturulurken hata oluştu: " + e.getMessage());
            throw new RuntimeException("Rapor oluşturulurken hata oluştu: " + e.getMessage());
        }
    }
}
