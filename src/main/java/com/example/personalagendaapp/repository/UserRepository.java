package com.example.personalagendaapp.repository;

import com.example.personalagendaapp.exception.UserNotFoundException;
import com.example.personalagendaapp.model.User;
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
public class UserRepository {
    private final JdbcTemplate jdbcTemplate;
    private final RowMapper<User> rowMapper = (resultSet, rowIndex) -> new User(
            resultSet.getLong("id"),
            resultSet.getString("name"),
            resultSet.getString("email"),
            resultSet.getTimestamp("date")
    );

    public UserRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public User createUser(User user) {
        user.setDate(new Timestamp(System.currentTimeMillis()));
        String sql = "INSERT INTO users (name, email, password, date) VALUES (?, ?, ?, ?)";
        PreparedStatementCreator preparedStatementCreator = (connection) -> {
            PreparedStatement preparedStatement = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);

            preparedStatement.setString(1, user.getName());
            preparedStatement.setString(2, user.getEmail());
            preparedStatement.setString(3, user.getPassword());
            preparedStatement.setTimestamp(4, user.getDate());

            return preparedStatement;
        };

        GeneratedKeyHolder generatedKeyHolder = new GeneratedKeyHolder();
        jdbcTemplate.update(preparedStatementCreator, generatedKeyHolder);

        user.setId(generatedKeyHolder.getKey().longValue());
        return user;
    }

    public Optional<User> getUserById(long id) {
        String sql = "SELECT * FROM users u WHERE u.id = ?";

        List<User> users = jdbcTemplate.query(sql, rowMapper, id);

        return Util.getFirstItemFromList(users);
    }

    public Optional<User> getUserByEmail(String email) {
        String sql = "SELECT * FROM users u WHERE UPPER(u.email) = UPPER(?)";

        List<User> users = jdbcTemplate.query(sql, rowMapper, email);

        return Util.getFirstItemFromList(users);
    }

    public Optional<User> getUserByEmailAndPassword (String email, String password) {
        String sql = "SELECT * FROM users u WHERE UPPER(u.email) = UPPER(?) AND u.password = ?";

        List<User> users = jdbcTemplate.query(sql, rowMapper, email, password);

        return Util.getFirstItemFromList(users);
    }

    public void updateUser(long id, User user) {
        String sql = "UPDATE users u SET u.name = ?, u.email = ?, u.password = ? WHERE u.id = ?";

        int updatedRowCount = jdbcTemplate.update(sql, user.getName(), user.getEmail(), user.getPassword(), id);

        if (updatedRowCount == 0) {
            throw new UserNotFoundException();
        }
    }
}
