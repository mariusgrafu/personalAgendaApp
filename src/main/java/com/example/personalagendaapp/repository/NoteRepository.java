package com.example.personalagendaapp.repository;

import com.example.personalagendaapp.model.Note;
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
public class NoteRepository {
    private final JdbcTemplate jdbcTemplate;
    private final RowMapper<Note> rowMapper = (resultSet, rowIndex) -> new Note(
            resultSet.getLong("id"),
            resultSet.getLong("authorId"),
            resultSet.getLong("taskId"),
            resultSet.getTimestamp("date"),
            resultSet.getString("content")
    );

    public NoteRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public Note createNote(Note note) {
        note.setDate(new Timestamp(System.currentTimeMillis()));
        String sql = "INSERT INTO notes (authorId, taskId, date, content) VALUES (?, ?, ?, ?)";
        PreparedStatementCreator preparedStatementCreator = (connection) -> {
            PreparedStatement preparedStatement = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);

            preparedStatement.setLong(1, note.getAuthorId());
            preparedStatement.setLong(2, note.getTaskId());
            preparedStatement.setTimestamp(3, note.getDate());
            preparedStatement.setString(4, note.getContent());

            return preparedStatement;
        };
        GeneratedKeyHolder generatedKeyHolder = new GeneratedKeyHolder();
        jdbcTemplate.update(preparedStatementCreator, generatedKeyHolder);

        note.setId(generatedKeyHolder.getKey().longValue());
        return note;
    }

    public Optional<Note> getNoteById(long noteId) {
        String sql = "SELECT * FROM notes n WHERE n.id = ?";

        List<Note> notes = jdbcTemplate.query(sql, rowMapper, noteId);

        return Util.getFirstItemFromList(notes);
    }

    public List<Note> getNotesByTaskId(long taskId) {
        String sql = "SELECT * FROM notes n WHERE n.taskId = ?";

        return jdbcTemplate.query(sql, rowMapper, taskId);
    }

    public void deleteNotesForTask(long taskId) {
        String sql = "DELETE FROM notes WHERE taskId = ?";

        jdbcTemplate.update(sql, taskId);
    }
}
