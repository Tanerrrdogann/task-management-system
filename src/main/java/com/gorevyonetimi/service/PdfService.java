package com.gorevyonetimi.service;

import com.gorevyonetimi.model.Task;
import com.lowagie.text.Document;
import com.lowagie.text.Element;
import com.lowagie.text.Font;
import com.lowagie.text.Paragraph;
import com.lowagie.text.Phrase;
import com.lowagie.text.pdf.PdfPCell;
import com.lowagie.text.pdf.PdfPTable;
import com.lowagie.text.pdf.PdfWriter;

import org.springframework.stereotype.Service;

import java.awt.Color;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.util.List;
import java.util.stream.Stream;

@Service
public class PdfService {

    public ByteArrayInputStream generateTaskListPdf(List<Task> tasks) {
        try (ByteArrayOutputStream out = new ByteArrayOutputStream()) {
            Document document = new Document();
            PdfWriter.getInstance(document, out);
            document.open();

            Font titleFont = new Font(Font.HELVETICA, 18, Font.BOLD);
            Paragraph title = new Paragraph("Görev Listesi", titleFont);
            title.setAlignment(Element.ALIGN_CENTER);
            title.setSpacingAfter(20);
            document.add(title);

            PdfPTable table = new PdfPTable(6);
            table.setWidthPercentage(100);
            table.setWidths(new int[]{2, 4, 2, 3, 3, 2});

            addTableHeader(table);
            addRows(table, tasks);

            document.add(table);
            document.close();

            return new ByteArrayInputStream(out.toByteArray());
        } catch (Exception e) {
            throw new RuntimeException("PDF oluşturulurken hata oluştu: " + e.getMessage());
        }
    }

    private void addTableHeader(PdfPTable table) {
        Stream.of("Başlık", "Açıklama", "Öncelik", "E-posta", "Tarih", "Durum")
                .map(header -> {
                    PdfPCell cell = new PdfPCell();
                    cell.setPhrase(new Phrase(header));
                    cell.setBackgroundColor(Color.LIGHT_GRAY);
                    cell.setHorizontalAlignment(Element.ALIGN_CENTER);
                    return cell;
                })
                .forEach(table::addCell);
    }

    private void addRows(PdfPTable table, List<Task> tasks) {
        for (Task task : tasks) {
            table.addCell(task.getTitle());
            table.addCell(task.getDescription());
            table.addCell(task.getPriorityLevel());
            table.addCell(task.getEmail() != null ? task.getEmail() : "-");
            table.addCell(task.getDueDate() != null ? task.getDueDate().toString() : "-");
            table.addCell(task.isCompleted() ? "Tamamlandı" : "Bekliyor");
        }
    }
}
