package com.example.personalagendaapp.repository;

import com.example.personalagendaapp.exception.InviteNotFoundException;
import com.example.personalagendaapp.model.EventInvite;
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
public class EventInviteRepository {
    private final JdbcTemplate jdbcTemplate;
    private final RowMapper<EventInvite> rowMapper = (resultSet, rowIndex) -> new EventInvite(
            resultSet.getLong("id"),
            resultSet.getLong("eventId"),
            resultSet.getLong("userId"),
            resultSet.getTimestamp("sentDate")
    );

    public EventInviteRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public EventInvite createInvite(EventInvite eventInvite) {
        eventInvite.setSentDate(new Timestamp(System.currentTimeMillis()));
        String sql = "INSERT INTO eventInvites (eventId, userId, sentDate) VALUES (?, ?, ?)";
        PreparedStatementCreator preparedStatementCreator = (connection) -> {
            PreparedStatement preparedStatement = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);

            preparedStatement.setLong(1, eventInvite.getEventId());
            preparedStatement.setLong(2, eventInvite.getUserId());
            preparedStatement.setTimestamp(3, eventInvite.getSentDate());

            return preparedStatement;
        };
        GeneratedKeyHolder generatedKeyHolder = new GeneratedKeyHolder();
        jdbcTemplate.update(preparedStatementCreator, generatedKeyHolder);

        eventInvite.setId(generatedKeyHolder.getKey().longValue());
        return eventInvite;
    }

    public Optional<EventInvite> getInviteByEventAndUser(long eventId, long userId) {
        String sql = "SELECT * FROM eventInvites e WHERE e.eventId = ? AND e.userId = ?";

        List<EventInvite> invites = jdbcTemplate.query(sql, rowMapper, eventId, userId);

        return Util.getFirstItemFromList(invites);
    }

    public Optional<EventInvite> getInviteById(long id) {
        String sql = "SELECT * FROM eventInvites e WHERE e.id = ?";

        List<EventInvite> invites = jdbcTemplate.query(sql, rowMapper, id);

        return Util.getFirstItemFromList(invites);
    }

    public List<EventInvite> getAllInvitesForUser(long userId) {
        String sql = "SELECT * FROM eventInvites e WHERE e.userId = ?";

        return jdbcTemplate.query(sql, rowMapper, userId);
    }

    public void deleteInviteById(long inviteId) {
        String sql = "DELETE FROM eventInvites WHERE id = ?";

        int deletedRowsCount = jdbcTemplate.update(sql, inviteId);

        if (deletedRowsCount == 0) {
            throw new InviteNotFoundException();
        }
    }

    public void deleteInvitesForEvent(long eventId) {
        String sql = "DELETE FROM eventInvites WHERE eventId = ?";

        jdbcTemplate.update(sql, eventId);
    }
}
