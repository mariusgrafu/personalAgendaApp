package com.example.personalagendaapp.service;

import com.example.personalagendaapp.exception.BadRequestException;
import com.example.personalagendaapp.exception.CannotAcceptInviteException;
import com.example.personalagendaapp.exception.CannotDeleteInviteException;
import com.example.personalagendaapp.exception.InviteNotFoundException;
import com.example.personalagendaapp.model.Event;
import com.example.personalagendaapp.model.EventInvite;
import com.example.personalagendaapp.repository.EventInviteRepository;
import com.example.personalagendaapp.repository.EventRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class EventInviteService {
    private final EventInviteRepository eventInviteRepository;
    private final EventRepository eventRepository;

    public EventInviteService(EventInviteRepository eventInviteRepository, EventRepository eventRepository) {
        this.eventInviteRepository = eventInviteRepository;
        this.eventRepository = eventRepository;
    }

    public EventInvite createInvite(EventInvite eventInvite) {
        Optional<EventInvite> userAlreadyInvited = eventInviteRepository.getInviteByEventAndUser(eventInvite.getEventId(), eventInvite.getUserId());

        if (userAlreadyInvited.isPresent()) {
            throw new BadRequestException("This user was already invited to this event!");
        }

        return eventInviteRepository.createInvite(eventInvite);
    }

    public List<EventInvite> getAllInvitesForUser(long userId) {
        return eventInviteRepository.getAllInvitesForUser(userId);
    }

    public EventInvite getInviteById(long inviteId, long userId) {
        Optional<EventInvite> optionalEventInvite = eventInviteRepository.getInviteById(inviteId);

        if (!optionalEventInvite.isPresent()) {
            throw new InviteNotFoundException();
        }

        EventInvite eventInvite = optionalEventInvite.get();

        if (eventInvite.getUserId() == userId) {
            return eventInvite;
        }

        Optional<Event> event = eventRepository.getEventById(eventInvite.getEventId());

        if (event.isPresent() && event.get().getAuthorId() == userId) {
            return eventInvite;
        }

        throw new InviteNotFoundException();
    }

    public void deleteInviteById(long inviteId, long userId) {
        boolean canDelete = false;
        Optional<EventInvite> optionalEventInvite = eventInviteRepository.getInviteById(inviteId);

        if (!optionalEventInvite.isPresent()) {
            throw new InviteNotFoundException();
        }

        EventInvite eventInvite = optionalEventInvite.get();

        if (eventInvite.getUserId() == userId) {
            canDelete = true;
        }

        if (!canDelete) {
            Optional<Event> event = eventRepository.getEventById(eventInvite.getEventId());

            if (event.isPresent() && event.get().getAuthorId() == userId) {
                canDelete = true;
            }
        }

        if (canDelete) {
            eventInviteRepository.deleteInviteById(inviteId);
        } else {
            throw new CannotDeleteInviteException();
        }
    }

    public EventInvite acceptInvite(long inviteId, long userId) {
        Optional<EventInvite> optionalEventInvite = eventInviteRepository.getInviteById(inviteId);

        if (!optionalEventInvite.isPresent()) {
            throw new InviteNotFoundException();
        }

        EventInvite eventInvite = optionalEventInvite.get();

        if (eventInvite.getUserId() == userId) {
            eventInviteRepository.deleteInviteById(inviteId);

            return eventInvite;
        }

        throw new CannotAcceptInviteException();
    }

    public void deleteInvitesForEvent(long eventId) {
        eventInviteRepository.deleteInvitesForEvent(eventId);
    }
}
