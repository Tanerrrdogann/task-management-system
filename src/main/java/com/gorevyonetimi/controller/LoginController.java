package com.gorevyonetimi.controller;

import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import com.gorevyonetimi.service.TaskService;
@Controller
public class LoginController {

    private final TaskService taskService;

    public LoginController(TaskService taskService) {
        this.taskService = taskService;
    }

    @GetMapping("/")
    public String showLoginForm() {
        return "login";
    }

    @PostMapping("/login")
    public String login(@RequestParam String firstName,
                        @RequestParam String lastName,
                        @RequestParam String email,
                        HttpSession session) {
        String fullName = firstName + " " + lastName;
        session.setAttribute("userFullName", fullName);
        session.setAttribute("userEmail", email);
        return "redirect:/tasks";
    }

    @GetMapping("/logout")
    public String logout(HttpSession session) {
        session.invalidate();
        taskService.deleteAll();
        return "redirect:/";
    }
}
