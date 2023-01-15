package com.example.personalagendaapp.model;

import java.sql.Timestamp;

public class Event implements Authorable {
    private long id;
    private String name;
    private String location;
    private Timestamp date;
    private long authorId;
    private long categoryId;

    public Event() {
    }

    public Event(String name, String location, Timestamp date, long authorId, long categoryId) {
        this.name = name;
        this.location = location;
        this.date = date;
        this.authorId = authorId;
        this.categoryId = categoryId;
    }

    public Event(long id, String name, String location, Timestamp date, long authorId, long categoryId) {
        this.id = id;
        this.name = name;
        this.location = location;
        this.date = date;
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
