package com.example.personalagendaapp.model;

import java.sql.Timestamp;

public class EventInvite {
    private long id;
    private long eventId;
    private long userId;
    private Timestamp sentDate;

    public EventInvite() {
    }

    public EventInvite(long eventId, long userId, Timestamp sentDate) {
        this.eventId = eventId;
        this.userId = userId;
        this.sentDate = sentDate;
    }

    public EventInvite(long id, long eventId, long userId, Timestamp sentDate) {
        this.id = id;
        this.eventId = eventId;
        this.userId = userId;
        this.sentDate = sentDate;
    }

    public EventInvite(long eventId, long userId) {
        this.eventId = eventId;
        this.userId = userId;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public long getEventId() {
        return eventId;
    }

    public void setEventId(long eventId) {
        this.eventId = eventId;
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
