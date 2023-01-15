package com.example.personalagendaapp.repository;

import com.example.personalagendaapp.model.Category;
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
public class CategoryRepository {
    private final JdbcTemplate jdbcTemplate;
    private final RowMapper<Category> rowMapper = (resultSet, rowIndex) -> new Category(
            resultSet.getLong("id"),
            resultSet.getString("name")
    );

    public CategoryRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public Category createCategory(Category category) {
        String sql = "INSERT INTO categories (name) VALUES (?)";

        PreparedStatementCreator preparedStatementCreator = (connection) -> {
            PreparedStatement preparedStatement = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);

            preparedStatement.setString(1, category.getName());

            return preparedStatement;
        };
        GeneratedKeyHolder generatedKeyHolder = new GeneratedKeyHolder();

        jdbcTemplate.update(preparedStatementCreator, generatedKeyHolder);

        category.setId(generatedKeyHolder.getKey().longValue());
        return category;
    }

    public Optional<Category> getCategoryByName(String name) {
        String sql = "SELECT * FROM categories c WHERE UPPER(c.name) = UPPER(?)";

        List<Category> categories = jdbcTemplate.query(sql, rowMapper, name);

        return Util.getFirstItemFromList(categories);
    }

    public List<Category> getAllCategories() {
        String sql = "SELECT * FROM categories";

        return jdbcTemplate.query(sql, rowMapper);
    }

    public void deleteAll() {
        String sql = "DELETE FROM categories";
        jdbcTemplate.update(sql);
    }
}
