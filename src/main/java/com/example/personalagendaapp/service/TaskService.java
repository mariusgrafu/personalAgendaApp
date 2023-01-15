package com.example.personalagendaapp.service;

import com.example.personalagendaapp.exception.*;
import com.example.personalagendaapp.model.*;
import com.example.personalagendaapp.repository.TaskRepository;
import com.example.personalagendaapp.util.Util;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class TaskService {
    private final TaskRepository taskRepository;
    private final CollaboratorService collaboratorService;
    private final CategoryService categoryService;
    private final TaskInviteService taskInviteService;
    private final NoteService noteService;

    public TaskService(TaskRepository taskRepository, CollaboratorService collaboratorService, CategoryService categoryService, TaskInviteService taskInviteService, NoteService noteService) {
        this.taskRepository = taskRepository;
        this.collaboratorService = collaboratorService;
        this.categoryService = categoryService;
        this.taskInviteService = taskInviteService;
        this.noteService = noteService;
    }

    private void _addCollaboratorToTask(long collaboratorId, long taskId) {
        Collaborator collaborator = new Collaborator(taskId, collaboratorId);
        collaboratorService.createCollaborator(collaborator);
    }

    private boolean _isUserCollaboratorOfTask(long userId, long taskId) {
        Optional<Collaborator> collaborator = collaboratorService.getCollaborationsByUserAndTask(userId, taskId);

        return collaborator.isPresent();
    }

    private List<Long> _getListOfTaskIdsByUserCollaborations(long userId) {
        List<Collaborator> collaborations = collaboratorService.getCollaborationsForUser(userId);
        return collaborations.stream().map(collaborator -> collaborator.getTaskId()).toList();
    }

    @Transactional
    public Task createTask(Task task) {
        if (!task.areDatesValid()) {
            throw new TaskDatesInvalidException();
        }
        Task createdTask = taskRepository.createTask(task);
        _addCollaboratorToTask(createdTask.getAuthorId(), createdTask.getId());

        return createdTask;
    }

    public List<Task> getAllTasksForUser(long userId) {
        return taskRepository.getTasksFromList(_getListOfTaskIdsByUserCollaborations(userId));
    }

    public List<Task> getAllTasksForUserByCategory(long userId, String categoryName) {
        Category category = categoryService.getCategoryByName(categoryName);
        return taskRepository.getTasksFromListFilteredByCategory(_getListOfTaskIdsByUserCollaborations(userId), category.getId());
    }

    public Task getTaskById(long taskId) {
        Optional<Task> optionalTask = taskRepository.getTaskById(taskId);

        if (!optionalTask.isPresent()) {
            throw new TaskNotFoundException();
        }

        return optionalTask.get();
    }

    public Task getTaskByIdWithAuth(long taskId, long userId) {
        if (!_isUserCollaboratorOfTask(userId, taskId)) {
            throw new TaskNotFoundException();
        }

        return getTaskById(taskId);
    }

    public Task updateCategoryForTask(long taskId, String categoryName, long userId) {
        Task task = getTaskById(taskId);

        Util.assertNonAuthors(task, userId);

        Category category = categoryService.getCategoryByName(categoryName);

        taskRepository.updateTaskCategory(taskId, category.getId());

        return getTaskById(taskId);
    }

    public TaskInvite inviteUserToTask(long taskId, long inviteeId, long userId) {
        Task task = getTaskById(taskId);

        Util.assertNonAuthors(task, userId);

        if (inviteeId == userId) {
            throw new BadRequestException("You cannot invite yourself to a task!");
        }

        return taskInviteService.createInvite(new TaskInvite(taskId, inviteeId));
    }

    public List<TaskInvite> getAllInvitesForUser(long userId) {
        return taskInviteService.getAllInvitesForUser(userId);
    }

    public TaskInvite getInviteById(long inviteId, long userId) {
        return taskInviteService.getInviteById(inviteId, userId);
    }

    public void deleteInviteById(long inviteId, long userId) {
        taskInviteService.deleteInviteById(inviteId, userId);
    }

    @Transactional
    public void acceptInvite(long inviteId, long userId) {
        TaskInvite taskInvite = taskInviteService.acceptInvite(inviteId, userId);

        _addCollaboratorToTask(userId, taskInvite.getTaskId());
    }

    public Note addNote(Note note) {
        getTaskById(note.getTaskId());

        if (!_isUserCollaboratorOfTask(note.getAuthorId(), note.getTaskId())) {
            throw new CannotAddNoteException();
        }

        return noteService.createNote(note);
    }

    public Note getNoteById(long noteId, long userId) {
        Note note = noteService.getNoteById(noteId);

        if (!_isUserCollaboratorOfTask(userId, note.getTaskId())) {
            throw new NoteNotFoundException();
        }

        return note;
    }

    public List<Note> getNotesForTask(long taskId, long userId) {
        if (!_isUserCollaboratorOfTask(userId, taskId)) {
            throw new TaskNotFoundException();
        }

        return noteService.getNotesByTaskId(taskId);
    }

    @Transactional
    public void deleteTaskById(long taskId, long userId) {
        Task task = getTaskById(taskId);

        Util.assertNonAuthors(task, userId);

        taskInviteService.deleteInvitesForTask(taskId);
        collaboratorService.deleteCollaboratorsOfTask(taskId);
        noteService.deleteNotesForTask(taskId);
        taskRepository.deleteTaskById(taskId);
    }
}
