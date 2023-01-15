package com.example.personalagendaapp.repository;

import com.example.personalagendaapp.exception.TaskNotFoundException;
import com.example.personalagendaapp.model.Task;
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
public class TaskRepository {
    private final JdbcTemplate jdbcTemplate;

    private final RowMapper<Task> rowMapper = (resultSet, rowIndex) -> new Task(
            resultSet.getLong("id"),
            resultSet.getString("name"),
            resultSet.getTimestamp("startDate"),
            resultSet.getTimestamp("endDate"),
            resultSet.getString("description"),
            resultSet.getBoolean("isDone"),
            resultSet.getLong("authorId"),
            resultSet.getLong("categoryId")
    );

    public TaskRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public Task createTask(Task task) {
        String sql = "INSERT INTO tasks (name, startdate, endDate, description, isDone, authorId, categoryId) VALUES (?, ?, ?, ?, false, ?, ?)";

        PreparedStatementCreator preparedStatementCreator = (connection) -> {
            PreparedStatement preparedStatement = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);

            preparedStatement.setString(1, task.getName());
            preparedStatement.setTimestamp(2, task.getStartDate());
            preparedStatement.setTimestamp(3, task.getEndDate());
            preparedStatement.setString(4, task.getDescription());
            preparedStatement.setLong(5, task.getAuthorId());

            if (task.getCategoryId() == 0) {
                preparedStatement.setObject(6, null);
            } else {
                preparedStatement.setLong(5, task.getCategoryId());
            }

            return preparedStatement;
        };
        GeneratedKeyHolder generatedKeyHolder = new GeneratedKeyHolder();

        jdbcTemplate.update(preparedStatementCreator, generatedKeyHolder);

        task.setId(generatedKeyHolder.getKey().longValue());
        return task;
    }

    public Optional<Task> getTaskById(long taskId) {
        String sql = "SELECT * FROM tasks t WHERE t.id = ?";

        List<Task> tasks = jdbcTemplate.query(sql, rowMapper, taskId);

        return Util.getFirstItemFromList(tasks);
    }

    public List<Task> getTasksFromList(List<Long> taskList) {
        if (taskList == null || taskList.isEmpty()) {
            return List.of();
        }
        String sql = "SELECT * FROM tasks t WHERE t.id IN (?) ORDER BY date ASC";

        return jdbcTemplate.query(sql, rowMapper, Util.joinList(taskList));
    }

    public List<Task> getTasksFromListFilteredByCategory(List<Long> taskList, long categoryId) {
        if (taskList == null || taskList.isEmpty()) {
            return List.of();
        }
        String sql = "SELECT * FROM tasks t WHERE t.categoryId = ? AND t.id IN (?) ORDER BY date ASC";

        return jdbcTemplate.query(sql, rowMapper, categoryId, Util.joinList(taskList));
    }

    public void updateTaskCategory(long taskId, long categoryId) {
        String sql = "UPDATE tasks t SET t.categoryId = ? WHERE t.id = ?";

        int updatedRowCount = jdbcTemplate.update(sql, categoryId, taskId);

        if (updatedRowCount == 0) {
            throw new TaskNotFoundException();
        }
    }

    public void deleteTaskById(long taskId) {
        String sql = "DELETE FROM tasks WHERE id = ?";

        jdbcTemplate.update(sql, taskId);
    }
}
