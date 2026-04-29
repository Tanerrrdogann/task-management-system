package com.gorevyonetimi.repository;

import com.gorevyonetimi.model.Task;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface TaskRepository extends JpaRepository<Task, Long> {
    List<Task> findByPriorityLevel(String priorityLevel);  // Doğru alan ismiyle eşleşiyor
    List<Task> findByTitleContainingIgnoreCase(String title);
}
