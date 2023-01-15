package com.example.personalagendaapp.model;

import java.sql.Timestamp;

public class User {
    private long id;
    private String name;
    private String email;
    private String password;
    private Timestamp date;

    public User() {
    }

    public User(long id, String name, String email, String password, Timestamp date) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.password = password;
        this.date = date;
    }

    public User(long id, String name, String email, Timestamp date) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.date = date;
    }

    public User(String name, String email, String password) {
        this.name = name;
        this.email = email;
        this.password = password;
    }

    public User(String name, String email, String password, Timestamp date) {
        this.name = name;
        this.email = email;
        this.password = password;
        this.date = date;
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

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Timestamp getDate() {
        return date;
    }

    public void setDate(Timestamp date) {
        this.date = date;
    }
}
