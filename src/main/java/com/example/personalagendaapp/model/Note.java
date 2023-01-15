package com.example.personalagendaapp.model;

import java.sql.Timestamp;

public class Note {
    private long id;
    private long authorId;
    private long taskId;
    private Timestamp date;
    private String content;

    public Note() {
    }

    public Note(long id, long authorId, long taskId, Timestamp date, String content) {
        this.id = id;
        this.authorId = authorId;
        this.taskId = taskId;
        this.date = date;
        this.content = content;
    }

    public Note(long authorId, long taskId, String content) {
        this.authorId = authorId;
        this.taskId = taskId;
        this.content = content;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public long getAuthorId() {
        return authorId;
    }

    public void setAuthorId(long authorId) {
        this.authorId = authorId;
    }

    public long getTaskId() {
        return taskId;
    }

    public void setTaskId(long taskId) {
        this.taskId = taskId;
    }

    public Timestamp getDate() {
        return date;
    }

    public void setDate(Timestamp date) {
        this.date = date;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}
