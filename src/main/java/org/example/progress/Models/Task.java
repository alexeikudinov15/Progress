package org.example.progress.Models;

public class Task {
    // Поля
    private String title;
    private boolean completed;

    // Конструкторы
    public Task() {}

    public Task(String title) {
        this.title = title;
        this.completed = false;
    }

    // Геттеры и сеттеры
    public String getTitle() {
        return title;
    }
    public void setTitle(String title) {
        this.title = title;
    }

    public boolean isCompleted() {
        return completed;
    }
    public void setCompleted(boolean completed) {
        this.completed = completed;
    }
}
