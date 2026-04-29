package com.gorevyonetimi.controller;

import com.gorevyonetimi.model.Task;
import com.gorevyonetimi.service.PdfService;
import com.gorevyonetimi.service.QuoteService;
import com.gorevyonetimi.service.ReportService;
import com.gorevyonetimi.service.TaskService;
import com.gorevyonetimi.util.FileLogger;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.apache.commons.io.IOUtils;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.io.ByteArrayInputStream;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

@Controller
public class TaskWebController {

    private final TaskService taskService;
    private final PdfService pdfService;
    private final ReportService reportService;
    private final QuoteService quoteService;

    public TaskWebController(TaskService taskService, PdfService pdfService,
                             QuoteService quoteService, ReportService reportService) {
        this.taskService = taskService;
        this.pdfService = pdfService;
        this.reportService = reportService;
        this.quoteService = quoteService;
    }

    @GetMapping("/tasks")
    public String getAllTasks(@RequestParam(required = false) String search,
                              @RequestParam(required = false) Boolean filterStatus,
                              HttpSession session,
                              Model model) {

        if (session.getAttribute("userEmail") == null) {
            return "redirect:/login";
        }

        List<Task> tasks = taskService.getAllTasks();

        if (search != null && !search.isEmpty()) {
            tasks = tasks.stream()
                    .filter(task -> task.getTitle().toLowerCase().contains(search.toLowerCase()))
                    .toList();
            FileLogger.log("Görev araması yapıldı: " + search);
        }

        if (filterStatus != null) {
            tasks = tasks.stream()
                    .filter(task -> task.isCompleted() == filterStatus)
                    .toList();
            FileLogger.log("Görev durumu filtrelendi: " + (filterStatus ? "Tamamlananlar" : "Bekleyenler"));
        }

        model.addAttribute("tasks", tasks);
        model.addAttribute("quote", quoteService.fetchMotivationalQuote());
        model.addAttribute("userFullName", session.getAttribute("userFullName"));
        return "tasks";
    }

    @GetMapping("/task-form")
    public String showTaskForm(HttpSession session, Model model) {
        if (session.getAttribute("userEmail") == null) {
            return "redirect:/login";
        }
        model.addAttribute("priorityLevels", new String[]{"Düşük", "Orta", "Yüksek"});
        return "form";
    }

    @PostMapping("/tasks")
    public String addTask(@RequestParam String title,
                          @RequestParam String description,
                          @RequestParam String priorityLevel,
                          @RequestParam String dueDate,
                          HttpSession session) {

        String email = (String) session.getAttribute("userEmail");
        LocalDateTime parsedDueDate = LocalDateTime.parse(dueDate, DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm"));
        Task task = new Task(title, description, priorityLevel, email, parsedDueDate);
        taskService.createTask(task);
        return "redirect:/tasks";
    }

    @PostMapping("/tasks/complete")
    public String completeTask(@RequestParam Long id) {
        Task task = taskService.getTaskById(id);
        if (task != null) {
            task.setCompleted(true);
            taskService.updateTask(id, task);
        }
        return "redirect:/tasks";
    }

    @PostMapping("/tasks/delete")
    public String deleteTask(@RequestParam Long id) {
        taskService.deleteTask(id);
        return "redirect:/tasks";
    }

    @GetMapping("/tasks/pdf")
    public void generatePdf(HttpServletResponse response) {
        try {
            List<Task> tasks = taskService.getAllTasks();
            ByteArrayInputStream pdfStream = pdfService.generateTaskListPdf(tasks);

            response.setContentType("application/pdf");
            response.setHeader("Content-Disposition", "attachment; filename=task-list.pdf");
            IOUtils.copy(pdfStream, response.getOutputStream());
            response.flushBuffer();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @GetMapping("/tasks/report/daily")
    public void generateDailyReport(HttpServletResponse response) {
        try {
            ByteArrayInputStream pdf = reportService.generateDailyReport();
            response.setContentType("application/pdf");
            response.setHeader("Content-Disposition", "attachment; filename=daily-report.pdf");
            IOUtils.copy(pdf, response.getOutputStream());
            response.flushBuffer();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @GetMapping("/tasks/report/weekly")
    public void generateWeeklyReport(HttpServletResponse response) {
        try {
            ByteArrayInputStream pdf = reportService.generateWeeklyReport();
            response.setContentType("application/pdf");
            response.setHeader("Content-Disposition", "attachment; filename=weekly-report.pdf");
            IOUtils.copy(pdf, response.getOutputStream());
            response.flushBuffer();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
