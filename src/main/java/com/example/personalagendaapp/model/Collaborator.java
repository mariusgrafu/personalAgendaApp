package com.example.personalagendaapp.model;

public class Collaborator {
    private long taskId;
    private long userId;

    public Collaborator() {
    }

    public Collaborator(long taskId, long userId) {
        this.taskId = taskId;
        this.userId = userId;
    }

    public long getTaskId() {
        return taskId;
    }

    public void setTaskId(long taskId) {
        this.taskId = taskId;
    }

    public long getUserId() {
        return userId;
    }

    public void setUserId(long userId) {
        this.userId = userId;
    }
}
