package com.example.personalagendaapp.model;

import java.sql.Timestamp;

public class TaskInvite {
    private long id;
    private long taskId;
    private long userId;
    private Timestamp sentDate;

    public TaskInvite() {
    }

    public TaskInvite(long taskId, long userId) {
        this.taskId = taskId;
        this.userId = userId;
    }

    public TaskInvite(long id, long taskId, long userId, Timestamp sentDate) {
        this.id = id;
        this.taskId = taskId;
        this.userId = userId;
        this.sentDate = sentDate;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
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

    public Timestamp getSentDate() {
        return sentDate;
    }

    public void setSentDate(Timestamp sentDate) {
        this.sentDate = sentDate;
    }
}
