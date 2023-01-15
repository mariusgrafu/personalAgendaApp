package com.example.personalagendaapp.model;

import java.sql.Timestamp;

public class Task implements Authorable {
    private long id;
    private String name;
    private Timestamp startDate;
    private Timestamp endDate;
    private String description;
    private boolean isDone;
    private long authorId;
    private long categoryId;

    public Task() {
    }

    public Task(String name, Timestamp startDate, Timestamp endDate, String description, boolean isDone, long authorId, long categoryId) {
        this.name = name;
        this.startDate = startDate;
        this.endDate = endDate;
        this.description = description;
        this.isDone = isDone;
        this.authorId = authorId;
        this.categoryId = categoryId;
    }

    public Task(long id, String name, Timestamp startDate, Timestamp endDate, String description, boolean isDone, long authorId, long categoryId) {
        this.id = id;
        this.name = name;
        this.startDate = startDate;
        this.endDate = endDate;
        this.description = description;
        this.isDone = isDone;
        this.authorId = authorId;
        this.categoryId = categoryId;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Timestamp getStartDate() {
        return startDate;
    }

    public void setStartDate(Timestamp startDate) {
        this.startDate = startDate;
    }

    public Timestamp getEndDate() {
        return endDate;
    }

    public void setEndDate(Timestamp endDate) {
        this.endDate = endDate;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public long getAuthorId() {
        return authorId;
    }

    public void setAuthorId(long authorId) {
        this.authorId = authorId;
    }

    public long getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(long categoryId) {
        this.categoryId = categoryId;
    }

    public boolean areDatesValid() {
        return getEndDate().after(getStartDate());
    }
}
