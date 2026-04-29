package com.gorevyonetimi.service;

import com.gorevyonetimi.model.Task;
import jakarta.mail.MessagingException;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TaskSchedulerService {

    private final TaskService taskService;
    private final MailService mailService;

    public TaskSchedulerService(TaskService taskService, MailService mailService) {
        this.taskService = taskService;
        this.mailService = mailService;
    }

    @Scheduled(cron = "0 0 13 * * ?")
    public void sendPriorityTaskEmails() {
        List<Task> priorityTasks = taskService.getPriorityTasks();
        if (!priorityTasks.isEmpty()) {
            for (Task task : priorityTasks) {
                try {
                    mailService.sendTaskReminderEmail(task);
                } catch (MessagingException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
