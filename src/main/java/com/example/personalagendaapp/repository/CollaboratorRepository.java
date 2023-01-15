package com.example.personalagendaapp.repository;

import com.example.personalagendaapp.model.Collaborator;
import com.example.personalagendaapp.util.Util;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public class CollaboratorRepository {
    private final JdbcTemplate jdbcTemplate;
    private final RowMapper<Collaborator> rowMapper = (resultSet, rowIndex) -> new Collaborator(
            resultSet.getLong("taskId"),
            resultSet.getLong("userId")
    );

    public CollaboratorRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public void createCollaborator(Collaborator collaborator) {
        String sql = "INSERT INTO collaborators (taskId, userId) VALUES (?, ?)";

        jdbcTemplate.update(sql, collaborator.getTaskId(), collaborator.getUserId());
    }

    public List<Collaborator> getCollaborationsForUser(long userId) {
        String sql = "SELECT * FROM collaborators c WHERE c.userId = ?";

        return jdbcTemplate.query(sql, rowMapper, userId);
    }

    public Optional<Collaborator> getCollaborationsByUserAndTask(long userId, long taskId) {
        String sql = "SELECT * FROM collaborators c WHERE c.userId = ? AND c.taskId = ?";

        List<Collaborator> collaborators = jdbcTemplate.query(sql, rowMapper, userId, taskId);

        return Util.getFirstItemFromList(collaborators);
    }

    public void deleteCollaboratorsOfTask(long taskId) {
        String sql = "DELETE FROM collaborators WHERE taskId = ?";

        jdbcTemplate.update(sql, taskId);
    }
}
