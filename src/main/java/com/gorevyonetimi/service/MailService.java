package com.gorevyonetimi.service;

import com.gorevyonetimi.model.Task;
import com.gorevyonetimi.util.FileLogger;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

@Service
public class MailService {

    private final JavaMailSender mailSender;

    public MailService(JavaMailSender mailSender) {
        this.mailSender = mailSender;
    }

    public void sendTaskReminderEmail(Task task) throws MessagingException {
        if (task.getEmail() == null || task.getEmail().isEmpty()) {
            FileLogger.log("E-posta gönderilemedi: E-posta adresi eksik.");
            throw new IllegalArgumentException("E-posta adresi eksik.");
        }

        MimeMessage message = mailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message, true, "UTF-8");

        helper.setTo(task.getEmail());
        helper.setSubject("Görev Hatırlatması: " + task.getTitle());

        String content = """
                <html>
                <body>
                    <h2>Görev Hatırlatması</h2>
                    <p><strong>Başlık:</strong> %s</p>
                    <p><strong>Açıklama:</strong> %s</p>
                    <p><strong>Teslim Tarihi:</strong> %s</p>
                </body>
                </html>
                """.formatted(task.getTitle(), task.getDescription(), task.getDueDate());

        helper.setText(content, true); // true = HTML

        mailSender.send(message);
        FileLogger.log("E-posta gönderildi: " + task.getEmail() + " | Görev: " + task.getTitle());
    }
}
