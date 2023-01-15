package com.example.personalagendaapp.service;

import com.example.personalagendaapp.model.Attendee;
import com.example.personalagendaapp.repository.AttendeeRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AttendeeService {
    private final AttendeeRepository attendeeRepository;

    public AttendeeService(AttendeeRepository attendeeRepository) {
        this.attendeeRepository = attendeeRepository;
    }

    public void createAttendee(Attendee attendee) {
        attendeeRepository.createAttendee(attendee);
    }

    public List<Attendee> getAttendancesForUser(long userId) {
        return attendeeRepository.getAttendancesForUser(userId);
    }

    public void deleteAttendancesForEvent(long eventId) {
        attendeeRepository.deleteAttendancesForEvent(eventId);
    }
}
