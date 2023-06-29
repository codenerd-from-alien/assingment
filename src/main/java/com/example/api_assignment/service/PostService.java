package com.example.api_assignment.service;

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

public class PostService {
    private final JdbcTemplate jdbcTemplate;

    public PostService(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }
    public MemoResponseDto createPost(MemoRequestDto requestDto) {
        Post post = new Post(requestDto);
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

        // Entity -> ResponseDto
        MemoResponseDto memoResponseDto = new MemoResponseDto(post);

        return memoResponseDto;




    }

    public List<MemoResponseDto> getPost() {
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

    public Long updateMemo(Long id, MemoRequestDto requestDto) {

        Post post = findById(id);
        if(post != null) {
            // memo 내용 수정
            String sql = "UPDATE post SET username = ?, contents = ?, title = ?, password = ? WHERE id = ?";
            jdbcTemplate.update(sql, requestDto.getUsername(), requestDto.getContents(),requestDto.getTitle(), requestDto.getPassword() ,id);

            return id;
        } else {
            throw new IllegalArgumentException("선택한 메모는 존재하지 않습니다.");
        }
    }


    public Long deletePost(Long id) {
        Post post = findById(id);
        if(post != null){
            String sql ="DELETE FROM post WHERE id = ?";
            jdbcTemplate.update(sql, id);
            return id;
        }else{
            throw new IllegalArgumentException("선택한 메모는 존재하지 않습니다.");
        }
    }


    private Post findById(Long id) {
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


    public MemoResponseDto getData(Long id) {
        Post post = findById(id);
        if(post !=null){
            return new MemoResponseDto(post);
        }else {
            throw new IllegalArgumentException("선택한 메모는 존재하지 않습니다.");
        }
    }
}
