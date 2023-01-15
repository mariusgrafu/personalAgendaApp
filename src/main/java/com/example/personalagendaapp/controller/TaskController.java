package com.example.personalagendaapp.controller;

import com.example.personalagendaapp.dto.NoteRequest;
import com.example.personalagendaapp.dto.TaskRequest;
import com.example.personalagendaapp.mapper.TaskMapper;
import com.example.personalagendaapp.model.Note;
import com.example.personalagendaapp.model.Task;
import com.example.personalagendaapp.model.TaskInvite;
import com.example.personalagendaapp.service.AuthService;
import com.example.personalagendaapp.service.TaskService;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/tasks")
@Validated
public class TaskController {
    private final AuthService authService;
    private final TaskService taskService;
    private final TaskMapper taskMapper;

    public TaskController(AuthService authService, TaskService taskService, TaskMapper taskMapper) {
        this.authService = authService;
        this.taskService = taskService;
        this.taskMapper = taskMapper;
    }

    @PostMapping
    public ResponseEntity<Task> createTask(
            @Valid @RequestBody TaskRequest taskRequest,
            @RequestHeader String authorization
    ) {
        long userId = authService.validateAuthHeader(authorization);
        taskRequest.setAuthorId(userId);
        Task createdTask = taskService.createTask(taskMapper.taskRequestToTask(taskRequest));
        return ResponseEntity.created(URI.create("/tasks/" + createdTask.getId()))
                .body(createdTask);
    }

    @GetMapping("/{taskId}")
    public ResponseEntity<Task> getTaskById(
            @PathVariable long taskId,
            @RequestHeader String authorization
    ) {
        long userId = authService.validateAuthHeader(authorization);

        return ResponseEntity.ok(taskService.getTaskByIdWithAuth(taskId, userId));
    }

    @DeleteMapping("/{taskId}")
    public ResponseEntity deleteTaskById(
            @PathVariable long taskId,
            @RequestHeader String authorization
    ) {
        long userId = authService.validateAuthHeader(authorization);

        taskService.deleteTaskById(taskId, userId);

        return ResponseEntity.ok().build();
    }

    @GetMapping
    public ResponseEntity<List<Task>> getAllTasks(@RequestHeader String authorization) {
        long userId = authService.validateAuthHeader(authorization);

        return ResponseEntity.ok().body(taskService.getAllTasksForUser(userId));
    }

    @GetMapping("/category/{categoryName}")
    public ResponseEntity<List<Task>> getAllTasksByCategory(
            @PathVariable String categoryName,
            @RequestHeader String authorization
    ) {
        long userId = authService.validateAuthHeader(authorization);

        return ResponseEntity.ok().body(taskService.getAllTasksForUserByCategory(userId, categoryName));
    }

    @PutMapping("/{taskId}/category/{categoryName}")
    public ResponseEntity<Task> updateTaskCategory(
            @PathVariable long taskId,
            @PathVariable String categoryName,
            @RequestHeader String authorization
    ) {
        long userId = authService.validateAuthHeader(authorization);

        return ResponseEntity.ok().body(taskService.updateCategoryForTask(taskId, categoryName, userId));
    }

    @PostMapping("/{taskId}/invite/{inviteeId}")
    public ResponseEntity<TaskInvite> inviteUserToTask(
            @PathVariable long taskId,
            @PathVariable long inviteeId,
            @RequestHeader String authorization
    ) {
        long userId = authService.validateAuthHeader(authorization);

        TaskInvite createdInvite = taskService.inviteUserToTask(taskId, inviteeId, userId);
        return ResponseEntity.created(URI.create("/tasks/invites/" + createdInvite.getId())).body(createdInvite);
    }

    @GetMapping("/invites")
    public ResponseEntity<List<TaskInvite>> getInvitesForCurrentUser(
            @RequestHeader String authorization
    ) {
        long userId = authService.validateAuthHeader(authorization);

        return ResponseEntity.ok().body(taskService.getAllInvitesForUser(userId));
    }

    @GetMapping("/invites/{inviteId}")
    public ResponseEntity<TaskInvite> getInviteById(
            @PathVariable long inviteId,
            @RequestHeader String authorization
    ) {
        long userId = authService.validateAuthHeader(authorization);

        return ResponseEntity.ok().body(taskService.getInviteById(inviteId, userId));
    }

    @DeleteMapping("/invites/{inviteId}")
    public ResponseEntity deleteInviteById(
            @PathVariable long inviteId,
            @RequestHeader String authorization
    ) {
        long userId = authService.validateAuthHeader(authorization);

        taskService.deleteInviteById(inviteId, userId);

        return ResponseEntity.ok().build();
    }

    @PostMapping("/invites/{inviteId}/accept")
    public ResponseEntity acceptInviteById(
            @PathVariable long inviteId,
            @RequestHeader String authorization
    ) {
        long userId = authService.validateAuthHeader(authorization);

        taskService.acceptInvite(inviteId, userId);

        return ResponseEntity.ok().build();
    }

    @PostMapping("/{taskId}/notes")
    public ResponseEntity<Note> addNoteToTask(
            @PathVariable long taskId,
            @Valid @RequestBody NoteRequest noteRequest,
            @RequestHeader String authorization
    ) {
        long userId = authService.validateAuthHeader(authorization);

        Note createdNote = taskService.addNote(new Note(userId, taskId, noteRequest.getContent()));

        return ResponseEntity.created(URI.create("/tasks/notes/" + createdNote.getId())).body(createdNote);
    }

    @GetMapping("/notes/{noteId}")
    public ResponseEntity<Note> getNoteById(
            @PathVariable long noteId,
            @RequestHeader String authorization
    ) {
        long userId = authService.validateAuthHeader(authorization);

        return ResponseEntity.ok(taskService.getNoteById(noteId, userId));
    }

    @GetMapping("/{taskId}/notes")
    public ResponseEntity<List<Note>> getNotesByTaskId(
            @PathVariable long taskId,
            @RequestHeader String authorization
    ) {
        long userId = authService.validateAuthHeader(authorization);

        return ResponseEntity.ok(taskService.getNotesForTask(taskId, userId));
    }
}
