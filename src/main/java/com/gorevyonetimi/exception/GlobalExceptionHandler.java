package com.gorevyonetimi.exception;

import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.ui.Model;
import jakarta.servlet.http.HttpServletRequest;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(TaskNotFoundException.class)
    public String handleTaskNotFound(TaskNotFoundException ex, HttpServletRequest request, Model model) {
        model.addAttribute("errorMessage", ex.getMessage());
        return "tasks";
    }

    @ExceptionHandler(Exception.class)
    public String handleGenericException(Exception ex, HttpServletRequest request, Model model) {
        model.addAttribute("errorMessage", "Beklenmeyen bir hata oluştu: " + ex.getMessage());
        return "tasks";
    }
}


