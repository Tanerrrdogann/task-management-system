package com.gorevyonetimi.repository;

import com.gorevyonetimi.model.Task;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.time.LocalDateTime;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
public class TaskRepositoryTest {

    @Autowired
    private TaskRepository taskRepository;

    @Test
    @DisplayName("Belirli öncelik seviyesine göre görevler bulunmalı")
    public void testFindByPriorityLevel() {
        Task task = new Task();
        task.setTitle("Öncelikli Görev");
        task.setDescription("Yüksek öncelik açıklaması");
        task.setPriorityLevel("Yüksek");
        task.setDueDate(LocalDateTime.now().plusDays(2));
        task.setCompleted(false);

        taskRepository.save(task);

        List<Task> tasks = taskRepository.findByPriorityLevel("Yüksek");

        assertThat(tasks).isNotEmpty();
        assertThat(tasks.get(0).getPriorityLevel()).isEqualTo("Yüksek");
    }

    @Test
    @DisplayName("Başlığa göre arama yapılmalı (büyük/küçük harf duyarsız)")
    public void testFindByTitleContainingIgnoreCase() {
        Task task = new Task();
        task.setTitle("Test Başlığı");
        task.setDescription("Açıklama");
        task.setPriorityLevel("Orta");
        task.setDueDate(LocalDateTime.now().plusDays(1));
        task.setCompleted(false);

        taskRepository.save(task);

        List<Task> results = taskRepository.findByTitleContainingIgnoreCase("baş");

        assertThat(results).isNotEmpty();
        assertThat(results.get(0).getTitle()).containsIgnoringCase("baş");
    }
}
