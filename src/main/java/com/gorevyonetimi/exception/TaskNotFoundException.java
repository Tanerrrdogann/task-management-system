package com.gorevyonetimi.exception;

public class TaskNotFoundException extends RuntimeException {
    public TaskNotFoundException(Long id) {
        super("Görev bulunamadı: ID = " + id);
    }
}
