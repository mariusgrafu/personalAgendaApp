package com.example.personalagendaapp.service;

import com.example.personalagendaapp.exception.BadRequestException;
import com.example.personalagendaapp.exception.EventNotFoundException;
import com.example.personalagendaapp.model.Attendee;
import com.example.personalagendaapp.model.Category;
import com.example.personalagendaapp.model.Event;
import com.example.personalagendaapp.model.EventInvite;
import com.example.personalagendaapp.repository.EventRepository;
import com.example.personalagendaapp.util.Util;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class EventService {
    private final EventRepository eventRepository;
    private final AttendeeService attendeeService;
    private final CategoryService categoryService;
    private final EventInviteService eventInviteService;

    private void _addAttendeeToEvent(long attendeeId, long eventId) {
        Attendee attendee = new Attendee(eventId, attendeeId);
        attendeeService.createAttendee(attendee);
    }

    private List<Long> _getListOfEventIdsByUserAttendances(long userId) {
        List<Attendee> attendances = attendeeService.getAttendancesForUser(userId);
        return attendances.stream().map((attendee -> attendee.getEventId())).toList();
    }

    public EventService(EventRepository eventRepository, AttendeeService attendeeService, CategoryService categoryService, EventInviteService eventInviteService) {
        this.eventRepository = eventRepository;
        this.attendeeService = attendeeService;
        this.categoryService = categoryService;
        this.eventInviteService = eventInviteService;
    }

    @Transactional
    public Event createEvent(Event event) {
        Event createdEvent = eventRepository.createEvent(event);
        _addAttendeeToEvent(createdEvent.getAuthorId(), createdEvent.getId());

        return createdEvent;
    }

    public List<Event> getAllEventsForUser(long userId) {
        return eventRepository.getEventsFromList(_getListOfEventIdsByUserAttendances(userId));
    }

    public List<Event> getAllEventsForUserByCategory(long userId, String categoryName) {
        Category category = categoryService.getCategoryByName(categoryName);

        return eventRepository.getEventsFromListFilteredByCategory(
                _getListOfEventIdsByUserAttendances(userId),
                category.getId()
        );
    }

    public Event getEventById(long eventId) {
        Optional<Event> optionalEvent = eventRepository.getEventById(eventId);

        if (!optionalEvent.isPresent()) {
            throw new EventNotFoundException();
        }

        return optionalEvent.get();
    }

    public Event getEventByIdWithAuth(long eventId, long userId) {
        Event event = getEventById(eventId);

        if (event.getAuthorId() != userId) {
            throw new EventNotFoundException();
        }

        return event;
    }

    public Event updateCategoryForEvent(long eventId, String categoryName, long userId) {
        Event event = getEventById(eventId);

        Util.assertNonAuthors(event, userId);

        Category category = categoryService.getCategoryByName(categoryName);

        eventRepository.updateEventCategory(eventId, category.getId());

        return getEventById(eventId);
    }

    public EventInvite inviteUserToEvent(long eventId, long inviteeId, long userId) {
        Event event = getEventById(eventId);

        Util.assertNonAuthors(event, userId);

        if (inviteeId == userId) {
            throw new BadRequestException("You cannot invite yourself to an event!");
        }

        return eventInviteService.createInvite(new EventInvite(eventId, inviteeId));
    }

    public List<EventInvite> getAllInvitesForUser(long userId) {
        return eventInviteService.getAllInvitesForUser(userId);
    }

    public EventInvite getInviteById(long inviteId, long userId) {
        return eventInviteService.getInviteById(inviteId, userId);
    }

    public void deleteInviteById(long inviteId, long userId) {
        eventInviteService.deleteInviteById(inviteId, userId);
    }

    @Transactional
    public void acceptInvite(long inviteId, long userId) {
        EventInvite eventInvite = eventInviteService.acceptInvite(inviteId, userId);

        _addAttendeeToEvent(userId, eventInvite.getEventId());
    }

    @Transactional
    public void deleteEventById(long eventId, long userId) {
        Event event = getEventById(eventId);

        Util.assertNonAuthors(event, userId);

        eventInviteService.deleteInvitesForEvent(eventId);
        attendeeService.deleteAttendancesForEvent(eventId);
        eventRepository.deleteEventById(eventId);
    }
}
