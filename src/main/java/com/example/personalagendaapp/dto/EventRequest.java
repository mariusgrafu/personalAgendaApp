package com.example.personalagendaapp.dto;

import javax.validation.constraints.*;

import java.sql.Timestamp;

public class EventRequest {
    @NotNull
    @NotBlank
    @Size(max=100)
    private String name;
    @NotNull
    @NotBlank
    @Size(max=100)
    private String location;
    @NotNull
    @FutureOrPresent(message="You cannot set an event in the past.")
    private Timestamp date;
    @NotNull
    private long authorId;
    private long categoryId;

    public EventRequest() {
    }

    public EventRequest(String name, String location, Timestamp date, long authorId, long categoryId) {
        this.name = name;
        this.location = location;
        this.date = date;
        this.authorId = authorId;
        this.categoryId = categoryId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public Timestamp getDate() {
        return date;
    }

    public void setDate(Timestamp date) {
        this.date = date;
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
