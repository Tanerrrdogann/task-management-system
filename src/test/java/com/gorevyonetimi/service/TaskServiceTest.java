package com.gorevyonetimi.service;

import com.gorevyonetimi.exception.TaskNotFoundException;
import com.gorevyonetimi.model.Task;
import com.gorevyonetimi.repository.TaskRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.time.LocalDateTime;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class TaskServiceTest {

    @Mock
    private TaskRepository taskRepository;

    @Mock
    private MailService mailService;

    @InjectMocks
    private TaskService taskService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testCreateTask_Success() {
        Task task = new Task("Başlık", "Açıklama", "Orta", null, LocalDateTime.now());
        when(taskRepository.save(task)).thenReturn(task);

        Task result = taskService.createTask(task);

        assertEquals("Başlık", result.getTitle());
        verify(taskRepository).save(task);
    }

    @Test
    void testCreateTask_MissingEmailForHighPriority() {
        Task task = new Task("Acil", "Test", "Yüksek", null, LocalDateTime.now());

        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            taskService.createTask(task);
        });

        assertEquals("Yüksek öncelikli görevler için e-posta adresi gereklidir.", exception.getMessage());
    }

    @Test
    void testGetTaskById_Found() {
        Task task = new Task("Başlık", "Açıklama", "Düşük", null, LocalDateTime.now());
        task.setId(1L);
        when(taskRepository.findById(1L)).thenReturn(Optional.of(task));

        Task found = taskService.getTaskById(1L);

        assertEquals(1L, found.getId());
    }

    @Test
    void testGetTaskById_NotFound() {
        when(taskRepository.findById(99L)).thenReturn(Optional.empty());

        assertThrows(TaskNotFoundException.class, () -> taskService.getTaskById(99L));
    }
}
