package com.gorevyonetimi.service;

import org.junit.jupiter.api.Test;

import java.io.ByteArrayInputStream;
import java.util.Collections;

import static org.junit.jupiter.api.Assertions.*;

public class PdfServiceTest {

    @Test
    void shouldGeneratePdfSuccessfully() {
        PdfService service = new PdfService();
        ByteArrayInputStream pdf = service.generateTaskListPdf(Collections.emptyList());
        assertNotNull(pdf);
    }
}