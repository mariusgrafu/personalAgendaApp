package com.example.personalagendaapp.repository;

import com.example.personalagendaapp.exception.EventNotFoundException;
import com.example.personalagendaapp.model.Event;
import com.example.personalagendaapp.util.Util;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.stereotype.Repository;

import java.sql.PreparedStatement;
import java.sql.Statement;
import java.util.List;
import java.util.Optional;

@Repository
public class EventRepository {
    private final JdbcTemplate jdbcTemplate;
    private final RowMapper<Event> rowMapper = (resultSet, rowIndex) -> new Event(
            resultSet.getLong("id"),
            resultSet.getString("name"),
            resultSet.getString("location"),
            resultSet.getTimestamp("date"),
            resultSet.getLong("authorId"),
            resultSet.getLong("categoryId")
    );

    public EventRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public Event createEvent(Event event) {
        String sql = "INSERT INTO events (name, location, date, authorId, categoryId) VALUES (?, ?, ?, ?, ?)";

        PreparedStatementCreator preparedStatementCreator = (connection) -> {
            PreparedStatement preparedStatement = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);

            preparedStatement.setString(1, event.getName());
            preparedStatement.setString(2, event.getLocation());
            preparedStatement.setTimestamp(3, event.getDate());
            preparedStatement.setLong(4, event.getAuthorId());
            if (event.getCategoryId() == 0) {
                preparedStatement.setObject(5, null);
            } else {
                preparedStatement.setLong(5, event.getCategoryId());
            }

            return preparedStatement;
        };
        GeneratedKeyHolder generatedKeyHolder = new GeneratedKeyHolder();

        jdbcTemplate.update(preparedStatementCreator, generatedKeyHolder);

        event.setId(generatedKeyHolder.getKey().longValue());
        return event;
    }

    public List<Event> getEventsFromList(List<Long> eventList) {
        if (eventList == null || eventList.isEmpty()) {
            return List.of();
        }
        String sql = "SELECT * FROM events e WHERE e.id IN (?) ORDER BY date ASC";

        return jdbcTemplate.query(
                sql,
                rowMapper,
                Util.joinList(eventList)
        );
    }

    public List<Event> getEventsFromListFilteredByCategory(List<Long> eventList, long categoryId) {
        if (eventList == null || eventList.isEmpty()) {
            return List.of();
        }
        String sql = "SELECT * FROM events e WHERE e.categoryId = ? AND e.id IN (?) ORDER BY date ASC";

        return jdbcTemplate.query(
                sql,
                rowMapper,
                categoryId,
                Util.joinList(eventList)
        );
    }

    public Optional<Event> getEventById(long eventId) {
        String sql = "SELECT * FROM events e WHERE e.id = ?";

        List<Event> events = jdbcTemplate.query(sql, rowMapper, eventId);

        return Util.getFirstItemFromList(events);
    }

    public void updateEventCategory(long eventId, long categoryId) {
        String sql = "UPDATE events e SET e.categoryId = ? WHERE e.id = ?";

        int updatedRowCount = jdbcTemplate.update(sql, categoryId, eventId);

        if (updatedRowCount == 0) {
            throw new EventNotFoundException();
        }
    }

    public void deleteEventById(long eventId) {
        String sql = "DELETE FROM events WHERE id = ?";

        jdbcTemplate.update(sql, eventId);
    }
}
