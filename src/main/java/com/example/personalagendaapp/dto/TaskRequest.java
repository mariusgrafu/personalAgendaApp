package com.example.personalagendaapp.dto;

import javax.validation.constraints.*;

import java.sql.Timestamp;

public class TaskRequest {
    @NotNull
    @NotBlank
    @Size(max=100)
    private String name;
    @NotNull
    @FutureOrPresent(message="A task cannot start in the past.")
    private Timestamp startDate;
    @NotNull
    private Timestamp endDate;
    @NotNull
    @NotBlank
    @Size(max=100)
    private String description;
    @NotNull
    private long authorId;
    private long categoryId;

    public TaskRequest() {
    }

    public TaskRequest(String name, Timestamp startDate, Timestamp endDate, String description, long authorId, long categoryId) {
        this.name = name;
        this.startDate = startDate;
        this.endDate = endDate;
        this.description = description;
        this.authorId = authorId;
        this.categoryId = categoryId;
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
}
