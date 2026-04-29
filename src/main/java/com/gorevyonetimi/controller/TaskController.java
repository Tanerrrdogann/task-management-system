package com.gorevyonetimi.controller;

import com.gorevyonetimi.model.Task;
import com.gorevyonetimi.service.TaskService;
import com.gorevyonetimi.util.FileLogger;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/tasks")
public class TaskController {

    private final TaskService taskService;

    public TaskController(TaskService taskService) {
        this.taskService = taskService;
    }

    @GetMapping
    public List<Task> getAllTasks() {
        FileLogger.log("Tüm görevler API ile çağrıldı.");
        return taskService.getAllTasks();
    }

    @GetMapping("/get/{id}")
    public ResponseEntity<Task> getTaskById(@PathVariable Long id) {
        FileLogger.log("Görev API ile getiriliyor: ID=" + id);
        Task task = taskService.getTaskById(id);
        return ResponseEntity.ok(task);
    }

    @PostMapping
    public Task createTask(@RequestBody Task task) {
        Task created = taskService.createTask(task);
        FileLogger.log("API ile görev oluşturuldu: " + created.getTitle());
        return created;
    }

    @PutMapping("/{id}")
    public ResponseEntity<Task> updateTask(@PathVariable Long id, @RequestBody Task task) {
        Task updated = taskService.updateTask(id, task);
        if (updated != null) {
            FileLogger.log("API ile görev güncellendi: ID=" + id);
            return ResponseEntity.ok(updated);
        } else {
            FileLogger.log("Görev güncellenemedi (bulunamadı): ID=" + id);
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteTask(@PathVariable Long id) {
        taskService.deleteTask(id);
        FileLogger.log("API ile görev silindi: ID=" + id);
        return ResponseEntity.noContent().build();
    }
}
