package com.example.personalagendaapp.model;

public class Attendee {
    private long eventId;
    private long userId;

    public Attendee() {
    }

    public Attendee(long eventId, long userId) {
        this.eventId = eventId;
        this.userId = userId;
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
}
