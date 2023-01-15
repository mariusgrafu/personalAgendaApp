package com.example.personalagendaapp.controller;

import com.example.personalagendaapp.dto.EventRequest;
import com.example.personalagendaapp.mapper.EventMapper;
import com.example.personalagendaapp.model.Event;
import com.example.personalagendaapp.model.EventInvite;
import com.example.personalagendaapp.service.AuthService;
import com.example.personalagendaapp.service.EventService;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/events")
@Validated
public class EventController {
    private final AuthService authService;
    private final EventService eventService;
    private final EventMapper eventMapper;

    public EventController(AuthService authService, EventService eventService, EventMapper eventMapper) {
        this.authService = authService;
        this.eventService = eventService;
        this.eventMapper = eventMapper;
    }

    @PostMapping
    public ResponseEntity<Event> createEvent(
            @Valid @RequestBody EventRequest eventRequest,
            @RequestHeader String authorization
    ) {
        long userId = authService.validateAuthHeader(authorization);
        eventRequest.setAuthorId(userId);
        Event createdEvent = eventService.createEvent(eventMapper.eventRequestToEvent(eventRequest));
        return ResponseEntity.created(URI.create("/events/" + createdEvent.getId()))
                .body(createdEvent);
    }

    @GetMapping("/{eventId}")
    public ResponseEntity<Event> getEventById(
            @PathVariable long eventId,
            @RequestHeader String authorization
    ) {
        long userId = authService.validateAuthHeader(authorization);

        return ResponseEntity.ok(eventService.getEventByIdWithAuth(eventId, userId));
    }

    @DeleteMapping("/{eventId}")
    public ResponseEntity deleteEventById(
            @PathVariable long eventId,
            @RequestHeader String authorization
    ) {
        long userId = authService.validateAuthHeader(authorization);

        eventService.deleteEventById(eventId, userId);

        return ResponseEntity.ok().build();
    }

    @GetMapping
    public ResponseEntity<List<Event>> getAllEvents(@RequestHeader String authorization) {
        long userId = authService.validateAuthHeader(authorization);

        return ResponseEntity.ok().body(eventService.getAllEventsForUser(userId));
    }

    @GetMapping("/category/{categoryName}")
    public ResponseEntity<List<Event>> getAllEventsByCategory(
            @PathVariable String categoryName,
            @RequestHeader String authorization
    ) {
        long userId = authService.validateAuthHeader(authorization);

        return ResponseEntity.ok().body(eventService.getAllEventsForUserByCategory(userId, categoryName));
    }

    @PutMapping("/{eventId}/category/{categoryName}")
    public ResponseEntity<Event> updateEventCategory(
            @PathVariable long eventId,
            @PathVariable String categoryName,
            @RequestHeader String authorization
    ) {
        long userId = authService.validateAuthHeader(authorization);

        return ResponseEntity.ok().body(eventService.updateCategoryForEvent(eventId, categoryName, userId));
    }

    @PostMapping("/{eventId}/invite/{inviteeId}")
    public ResponseEntity<EventInvite> inviteUserToEvent(
            @PathVariable long eventId,
            @PathVariable long inviteeId,
            @RequestHeader String authorization
    ) {
        long userId = authService.validateAuthHeader(authorization);

        EventInvite createdInvite = eventService.inviteUserToEvent(eventId, inviteeId, userId);
        return ResponseEntity.created(URI.create("/events/invites/" + createdInvite.getId())).body(createdInvite);
    }

    @GetMapping("/invites")
    public ResponseEntity<List<EventInvite>> getInvitesForCurrentUser(
            @RequestHeader String authorization
    ) {
        long userId = authService.validateAuthHeader(authorization);

        return ResponseEntity.ok().body(eventService.getAllInvitesForUser(userId));
    }

    @GetMapping("/invites/{inviteId}")
    public ResponseEntity<EventInvite> getInviteById(
            @PathVariable long inviteId,
            @RequestHeader String authorization
    ) {
        long userId = authService.validateAuthHeader(authorization);

        return ResponseEntity.ok().body(eventService.getInviteById(inviteId, userId));
    }

    @DeleteMapping("/invites/{inviteId}")
    public ResponseEntity deleteInviteById(
            @PathVariable long inviteId,
            @RequestHeader String authorization
    ) {
        long userId = authService.validateAuthHeader(authorization);

        eventService.deleteInviteById(inviteId, userId);

        return ResponseEntity.ok().build();
    }

    @PostMapping("/invites/{inviteId}/accept")
    public ResponseEntity acceptInviteById(
            @PathVariable long inviteId,
            @RequestHeader String authorization
    ) {
        long userId = authService.validateAuthHeader(authorization);

        eventService.acceptInvite(inviteId, userId);

        return ResponseEntity.ok().build();
    }
}
