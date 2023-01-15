package com.example.personalagendaapp.mapper;

import com.example.personalagendaapp.dto.EventRequest;
import com.example.personalagendaapp.model.Event;
import org.springframework.stereotype.Component;

@Component
public class EventMapper {
    public Event eventRequestToEvent(EventRequest eventRequest) {
        return new Event(
                eventRequest.getName(),
                eventRequest.getLocation(),
                eventRequest.getDate(),
                eventRequest.getAuthorId(),
                eventRequest.getCategoryId()
        );
    }
}
