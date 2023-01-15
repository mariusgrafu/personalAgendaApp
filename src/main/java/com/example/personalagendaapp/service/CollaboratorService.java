package com.example.personalagendaapp.service;

import com.example.personalagendaapp.model.Collaborator;
import com.example.personalagendaapp.repository.CollaboratorRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CollaboratorService {
    private final CollaboratorRepository collaboratorRepository;

    public CollaboratorService(CollaboratorRepository collaboratorRepository) {
        this.collaboratorRepository = collaboratorRepository;
    }

    public void createCollaborator(Collaborator collaborator) {
        collaboratorRepository.createCollaborator(collaborator);
    }

    public List<Collaborator> getCollaborationsForUser(long userId) {
        return collaboratorRepository.getCollaborationsForUser(userId);
    }

    public Optional<Collaborator> getCollaborationsByUserAndTask(long userId, long taskId) {
        return collaboratorRepository.getCollaborationsByUserAndTask(userId, taskId);
    }

    public void deleteCollaboratorsOfTask(long taskId) {
        collaboratorRepository.deleteCollaboratorsOfTask(taskId);
    }
}
