package com.gorevyonetimi.service;

import com.gorevyonetimi.exception.TaskNotFoundException;
import com.gorevyonetimi.model.Task;
import com.gorevyonetimi.repository.TaskRepository;
import com.gorevyonetimi.util.FileLogger;
import jakarta.mail.MessagingException;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TaskService {

    private final TaskRepository taskRepository;
    private final MailService mailService;

    public TaskService(TaskRepository taskRepository, MailService mailService) {
        this.taskRepository = taskRepository;
        this.mailService = mailService;
    }

    public List<Task> getAllTasks() {
        FileLogger.log("Tüm görevler listelendi.");
        return taskRepository.findAll();
    }

    public void deleteAll() {
        taskRepository.deleteAll();
        FileLogger.log("Tüm görevler silindi (logout sonrası).");
    }

    public List<Task> getPriorityTasks() {
        FileLogger.log("Yüksek öncelikli görevler listelendi.");
        return taskRepository.findByPriorityLevel("Yüksek");
    }

    public Task getTaskById(Long id) {
        Task task = taskRepository.findById(id)
                .orElseThrow(() -> {
                    FileLogger.log("Görev bulunamadı: ID=" + id);
                    return new TaskNotFoundException(id);
                });
        FileLogger.log("Görev bulundu: ID=" + id);
        return task;
    }

    public Task createTask(Task task) {
        if ("Yüksek".equals(task.getPriorityLevel()) &&
                (task.getEmail() == null || task.getEmail().isEmpty())) {
            FileLogger.log("E-posta adresi eksik: Yüksek öncelikli görev eklenemedi.");
            throw new IllegalArgumentException("Yüksek öncelikli görevler için e-posta adresi gereklidir.");
        }
        Task saved = taskRepository.save(task);
        FileLogger.log("Yeni görev oluşturuldu: ID=" + saved.getId() + ", Başlık=" + saved.getTitle());
        return saved;
    }

    public Task updateTask(Long id, Task taskDetails) {
        Task task = getTaskById(id);
        task.setTitle(taskDetails.getTitle());
        task.setDescription(taskDetails.getDescription());
        task.setCompleted(taskDetails.isCompleted());
        task.setPriorityLevel(taskDetails.getPriorityLevel());
        task.setEmail(taskDetails.getEmail());
        task.setDueDate(taskDetails.getDueDate());
        Task updated = taskRepository.save(task);
        FileLogger.log("Görev güncellendi: ID=" + updated.getId());
        return updated;
    }

    public void deleteTask(Long id) {
        if (!taskRepository.existsById(id)) {
            FileLogger.log("Silinmek istenen görev bulunamadı: ID=" + id);
            throw new TaskNotFoundException(id);
        }
        taskRepository.deleteById(id);
        FileLogger.log("Görev silindi: ID=" + id);
    }

    public void sendReminderForHighPriorityTasks() {
        List<Task> highPriorityTasks = getPriorityTasks();
        for (Task task : highPriorityTasks) {
            try {
                mailService.sendTaskReminderEmail(task);
                FileLogger.log("Hatırlatma e-postası gönderildi: " + task.getTitle() + " -> " + task.getEmail());
            } catch (MessagingException e) {
                FileLogger.log("E-posta gönderilemedi: " + e.getMessage());
            }
        }
    }

    @Scheduled(cron = "0 0 13 * * ?")
    public void sendDailyReminderForHighPriorityTasks() {
        FileLogger.log("Zamanlanmış görev: Yüksek öncelikli görevler için e-posta gönderimi başlatıldı.");
        sendReminderForHighPriorityTasks();
    }
}
