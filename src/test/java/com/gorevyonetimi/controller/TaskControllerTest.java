package com.gorevyonetimi.controller;

import com.gorevyonetimi.model.Task;
import com.gorevyonetimi.service.TaskService;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

import static org.hamcrest.Matchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(TaskController.class)
@Import(TaskControllerTest.Config.class)
public class TaskControllerTest {

    @TestConfiguration
    static class Config {
        @Bean
        public TaskService taskService() {
            TaskService mockService = Mockito.mock(TaskService.class);
            Mockito.when(mockService.getAllTasks())
                    .thenReturn(List.of(new Task("Test", "Desc", "Orta", null, null)));
            return mockService;
        }
    }

    @Autowired
    private MockMvc mockMvc;

    @Test
    void shouldReturnAllTasks() throws Exception {
        mockMvc.perform(get("/api/tasks").accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].title", is("Test")));
    }
}
