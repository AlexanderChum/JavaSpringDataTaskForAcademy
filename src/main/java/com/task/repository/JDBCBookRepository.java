package com.task.repository;

import com.task.model.Book;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class JDBCBookRepository implements BookRepository {
    JdbcTemplate jdbcTemplate;
    static RowMapper<Book> rowMapper = (rs, rowNum) -> Book.builder()
            .id(rs.getObject("id", UUID.class))
            .title(rs.getString("title"))
            .author(rs.getString("author"))
            .publicationYear(rs.getInt("publication_Year"))
            .build();

    @Override
    public Book save(Book book) {
        if (book.getId() == null) {
            book.setId(UUID.randomUUID());
            insert(book);
        } else {
            update(book);
        }
        return book;
    }

    private void insert(Book book) {
        String sql = "INSERT INTO books (id, title, author, publication_year) VALUES (?, ?, ?, ?)";
        jdbcTemplate.update(sql,
                book.getId(),
                book.getTitle(),
                book.getAuthor(),
                book.getPublicationYear());
    }

    private void update(Book book) {
        String sql = "UPDATE books SET title = ?, author = ?, publication_year = ? WHERE id = ?";
        jdbcTemplate.update(sql,
                book.getTitle(),
                book.getAuthor(),
                book.getPublicationYear(),
                book.getId());
    }

    @Override
    public Optional<Book> findById(UUID id) {
        String sql = "SELECT * FROM books WHERE id = ?";
        try {
            Book book = jdbcTemplate.queryForObject(sql, rowMapper, id);
            return Optional.ofNullable(book);
        } catch (Exception e) {
            return Optional.empty();
        }
    }

    @Override
    public List<Book> findAll() {
        String sql = "SELECT * FROM books";
        return jdbcTemplate.query(sql, rowMapper);
    }

    @Override
    public void deleteById(UUID id) {
        String sql = "DELETE FROM books WHERE id = ?";
        jdbcTemplate.update(sql, id);
    }

    @Override
    public boolean existsById(UUID id) {
        String sql = "SELECT COUNT(*) FROM books WHERE id = ?";
        Integer count = jdbcTemplate.queryForObject(sql, Integer.class, id);
        return count != null && count > 0;
    }
}
