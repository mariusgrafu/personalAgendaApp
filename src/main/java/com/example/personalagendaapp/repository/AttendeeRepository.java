package com.example.personalagendaapp.repository;

import com.example.personalagendaapp.model.Attendee;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class AttendeeRepository {
    private final JdbcTemplate jdbcTemplate;
    private final RowMapper<Attendee> rowMapper = (resultSet, rowIndex) -> new Attendee(
            resultSet.getLong("eventId"),
            resultSet.getLong("userId")
    );

    public AttendeeRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public void createAttendee(Attendee attendee) {
        String sql = "INSERT INTO attendees (eventId, userId) VALUES (?, ?)";

        jdbcTemplate.update(sql, attendee.getEventId(), attendee.getUserId());
    }

    public List<Attendee> getAttendancesForUser(long userId) {
        String sql = "SELECT * FROM attendees a WHERE a.userId = ?";

        return jdbcTemplate.query(sql, rowMapper, userId);
    }

    public void deleteAttendancesForEvent(long eventId) {
        String sql = "DELETE FROM attendees WHERE eventId = ?";

        jdbcTemplate.update(sql, eventId);
    }
}
