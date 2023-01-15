package com.example.personalagendaapp.service;

import com.example.personalagendaapp.exception.BadRequestException;
import com.example.personalagendaapp.exception.CannotAcceptInviteException;
import com.example.personalagendaapp.exception.CannotDeleteInviteException;
import com.example.personalagendaapp.exception.InviteNotFoundException;
import com.example.personalagendaapp.model.Task;
import com.example.personalagendaapp.model.TaskInvite;
import com.example.personalagendaapp.repository.TaskInviteRepository;
import com.example.personalagendaapp.repository.TaskRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class TaskInviteService {
    private final TaskInviteRepository taskInviteRepository;
    private final TaskRepository taskRepository;

    public TaskInviteService(TaskInviteRepository taskInviteRepository, TaskRepository taskRepository) {
        this.taskInviteRepository = taskInviteRepository;
        this.taskRepository = taskRepository;
    }

    public TaskInvite createInvite(TaskInvite taskInvite) {
        Optional<TaskInvite> userAlreadyInvited = taskInviteRepository.getInviteByTaskAndUser(taskInvite.getTaskId(), taskInvite.getUserId());

        if (userAlreadyInvited.isPresent()) {
            throw new BadRequestException("This user was already invited to this task!");
        }

        return taskInviteRepository.createInvite(taskInvite);
    }

    public List<TaskInvite> getAllInvitesForUser(long userId) {
        return taskInviteRepository.getAllInvitesForUser(userId);
    }

    public TaskInvite getInviteById(long inviteId, long userId) {
        Optional<TaskInvite> optionalTaskInvite = taskInviteRepository.getInviteById(inviteId);

        if (!optionalTaskInvite.isPresent()) {
            throw new InviteNotFoundException();
        }

        TaskInvite taskInvite = optionalTaskInvite.get();

        if (taskInvite.getUserId() == userId) {
            return taskInvite;
        }

        Optional<Task> task = taskRepository.getTaskById(taskInvite.getTaskId());

        if (task.isPresent() && task.get().getAuthorId() == userId) {
            return taskInvite;
        }

        throw new InviteNotFoundException();
    }

    public void deleteInviteById(long inviteId, long userId) {
        boolean canDelete = false;
        Optional<TaskInvite> optionalTaskInvite = taskInviteRepository.getInviteById(inviteId);

        if (!optionalTaskInvite.isPresent()) {
            throw new InviteNotFoundException();
        }

        TaskInvite taskInvite = optionalTaskInvite.get();

        if (taskInvite.getUserId() == userId) {
            canDelete = true;
        }

        if (!canDelete) {
            Optional<Task> task = taskRepository.getTaskById(taskInvite.getTaskId());

            if (task.isPresent() && task.get().getAuthorId() == userId) {
                canDelete = true;
            }
        }

        if (canDelete) {
            taskInviteRepository.deleteInviteById(inviteId);
        } else {
            throw new CannotDeleteInviteException();
        }
    }

    public TaskInvite acceptInvite(long inviteId, long userId) {
        Optional<TaskInvite> optionalTaskInvite = taskInviteRepository.getInviteById(inviteId);

        if (!optionalTaskInvite.isPresent()) {
            throw new InviteNotFoundException();
        }

        TaskInvite taskInvite = optionalTaskInvite.get();

        if (taskInvite.getUserId() == userId) {
            taskInviteRepository.deleteInviteById(inviteId);

            return taskInvite;
        }

        throw new CannotAcceptInviteException();
    }

    public void deleteInvitesForTask(long taskId) {
        taskInviteRepository.deleteInvitesForTask(taskId);
    }
}
