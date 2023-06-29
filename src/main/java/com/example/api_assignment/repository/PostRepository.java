package com.example.api_assignment.repository;

import com.example.api_assignment.dto.MemoRequestDto;
import com.example.api_assignment.dto.MemoResponseDto;
import com.example.api_assignment.entity.Post;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;

public class PostRepository {

    private final JdbcTemplate jdbcTemplate;
    public PostRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public Post save(Post post) {//db 저장
        KeyHolder keyHolder = new GeneratedKeyHolder(); // 기본 키를 반환받기 위한 객체

        String sql = "INSERT INTO post (username, contents, title, password) VALUES (?, ?, ?, ?)";
        jdbcTemplate.update( con -> {
                    PreparedStatement preparedStatement = con.prepareStatement(sql,
                            Statement.RETURN_GENERATED_KEYS);

                    preparedStatement.setString(1, post.getUsername());
                    preparedStatement.setString(2, post.getContents());
                    preparedStatement.setString(3, post.getTitle());
                    preparedStatement.setString(4, post.getPassword());
                    return preparedStatement;
                },
                keyHolder);

        Long id = keyHolder.getKey().longValue();
        post.setId(id);
        return post;
    }

    public List<MemoResponseDto> findAll() {//db 다들고 옴
        String sql = "SELECT * FROM post";
        return jdbcTemplate.query(sql, new RowMapper<MemoResponseDto>() {
            @Override
            public MemoResponseDto mapRow(ResultSet rs, int rowNum) throws SQLException {
                // SQL 의 결과로 받아온 Memo 데이터들을 MemoResponseDto 타입으로 변환해줄 메서드
                Long id = rs.getLong("id");
                String username = rs.getString("username");
                String contents = rs.getString("contents");
                String title = rs.getString("title");
                String password = rs.getString("password");
                return new MemoResponseDto(id, username, contents, title, password);
            }
        });
    }


    public void delete(Long id) {
        String sql ="DELETE FROM post WHERE id = ?";
        jdbcTemplate.update(sql, id);
    }



    public void update(Long id, MemoRequestDto requestDto) {
        String sql = "UPDATE post SET username = ?, contents = ?, title = ?, password = ? WHERE id = ?";
        jdbcTemplate.update(sql, requestDto.getUsername(), requestDto.getContents(),requestDto.getTitle(), requestDto.getPassword() ,id);
    }
    public MemoResponseDto getOne(Long id) {
        String sql = "SELECT * FROM post WHERE id = ?";
        return jdbcTemplate.queryForObject(sql, new Object[]{id}, new RowMapper<MemoResponseDto>() {
            @Override
            public MemoResponseDto mapRow(ResultSet rs, int rowNum) throws SQLException {
                Long postId = rs.getLong("id");
                String username = rs.getString("username");
                String contents = rs.getString("contents");
                String title = rs.getString("title");
                String password = rs.getString("password");
                return new MemoResponseDto(postId, username, contents, title, password);
            }
        });
    }


    public Post findById(Long id) {
        String sql = "SELECT * FROM post WHERE id = ?";

        return jdbcTemplate.query(sql, resultSet -> {
            if(resultSet.next()) {
                Post post = new Post();
                post.setUsername(resultSet.getString("username"));
                post.setContents(resultSet.getString("contents"));
                post.setTitle(resultSet.getString("title"));
                post.setPassword(resultSet.getString("password"));
                return post;
            } else {
                return null;
            }
        }, id);
    }



}
