package com.gorevyonetimi.service;

import com.gorevyonetimi.model.Task;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDPageContentStream;
import org.apache.pdfbox.pdmodel.font.PDType1Font;
import org.springframework.stereotype.Service;
import com.gorevyonetimi.util.FileLogger;

import java.io.File;
import java.io.IOException;
import java.util.List;

@Service
public class PdfReportService {

    private static final String FILE_PATH = "reports/task_report.pdf";

    public void generateTaskReport(List<Task> tasks) {
        try (PDDocument document = new PDDocument()) {
            PDPage page = new PDPage();
            document.addPage(page);

            try (PDPageContentStream contentStream = new PDPageContentStream(document, page)) {
                contentStream.setFont(PDType1Font.HELVETICA_BOLD, 14);
                contentStream.beginText();
                contentStream.newLineAtOffset(50, 750);
                contentStream.showText("Görev Raporu");
                contentStream.setFont(PDType1Font.HELVETICA, 12);

                int yPosition = 720;
                for (Task task : tasks) {
                    contentStream.newLineAtOffset(0, -20);
                    if (yPosition < 100) break; // Sayfa dolarsa kır
                    contentStream.showText("- " + task.getTitle() + " | " + task.getDescription());
                    yPosition -= 20;
                }

                contentStream.endText();
            }

            File reportsDir = new File("reports");
            if (!reportsDir.exists()) {
                reportsDir.mkdirs();
            }

            document.save(FILE_PATH);
            FileLogger.log("PDF raporu oluşturuldu: " + FILE_PATH);

        } catch (IOException e) {
            FileLogger.log("PDF oluşturulurken hata: " + e.getMessage());
        }
    }
}
