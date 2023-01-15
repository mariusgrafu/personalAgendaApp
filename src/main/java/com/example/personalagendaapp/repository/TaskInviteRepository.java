package com.example.personalagendaapp.repository;

import com.example.personalagendaapp.exception.InviteNotFoundException;
import com.example.personalagendaapp.model.TaskInvite;
import com.example.personalagendaapp.util.Util;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.stereotype.Repository;

import java.sql.PreparedStatement;
import java.sql.Statement;
import java.sql.Timestamp;
import java.util.List;
import java.util.Optional;

@Repository
public class TaskInviteRepository {
    private final JdbcTemplate jdbcTemplate;
    private final RowMapper<TaskInvite> rowMapper = (resultSet, rowIndex) -> new TaskInvite(
            resultSet.getLong("id"),
            resultSet.getLong("taskId"),
            resultSet.getLong("userId"),
            resultSet.getTimestamp("sentDate")
    );

    public TaskInviteRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public TaskInvite createInvite(TaskInvite taskInvite) {
        taskInvite.setSentDate(new Timestamp(System.currentTimeMillis()));
        String sql = "INSERT INTO taskInvites (taskId, userId, sentDate) VALUES (?, ?, ?)";
        PreparedStatementCreator preparedStatementCreator = (connection) -> {
            PreparedStatement preparedStatement = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);

            preparedStatement.setLong(1, taskInvite.getTaskId());
            preparedStatement.setLong(2, taskInvite.getUserId());
            preparedStatement.setTimestamp(3, taskInvite.getSentDate());

            return preparedStatement;
        };
        GeneratedKeyHolder generatedKeyHolder = new GeneratedKeyHolder();
        jdbcTemplate.update(preparedStatementCreator, generatedKeyHolder);

        taskInvite.setId(generatedKeyHolder.getKey().longValue());
        return taskInvite;
    }

    public Optional<TaskInvite> getInviteById(long id) {
        String sql = "SELECT * FROM taskInvites ti WHERE ti.id = ?";

        List<TaskInvite> invites = jdbcTemplate.query(sql, rowMapper, id);

        return Util.getFirstItemFromList(invites);
    }

    public Optional<TaskInvite> getInviteByTaskAndUser(long taskId, long userId) {
        String sql = "SELECT * FROM taskInvites ti WHERE ti.taskId = ? AND ti.userId = ?";

        List<TaskInvite> invites = jdbcTemplate.query(sql, rowMapper, taskId, userId);

        return Util.getFirstItemFromList(invites);
    }

    public List<TaskInvite> getAllInvitesForUser(long userId) {
        String sql = "SELECT * FROM taskInvites ti WHERE ti.userId = ?";

        return jdbcTemplate.query(sql, rowMapper, userId);
    }

    public void deleteInviteById(long inviteId) {
        String sql = "DELETE FROM taskInvites WHERE id = ?";

        int deletedRowsCount = jdbcTemplate.update(sql, inviteId);

        if (deletedRowsCount == 0) {
            throw new InviteNotFoundException();
        }
    }

    public void deleteInvitesForTask(long taskId) {
        String sql = "DELETE FROM taskInvites WHERE taskId = ?";

        jdbcTemplate.update(sql, taskId);
    }
}
