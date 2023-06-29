package com.example.api_assignment.service;

import com.example.api_assignment.dto.MemoRequestDto;
import com.example.api_assignment.dto.MemoResponseDto;
import com.example.api_assignment.entity.Post;
import com.example.api_assignment.repository.PostRepository;
import org.springframework.jdbc.core.JdbcTemplate;

import java.util.List;

public class PostService {
    private final JdbcTemplate jdbcTemplate;

    public PostService(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }
    public MemoResponseDto createPost(MemoRequestDto requestDto) {
        Post post = new Post(requestDto);
        PostRepository postRepository = new PostRepository(jdbcTemplate);
        Post savePost = postRepository.save(post);//postrepository에 디비를 저장할 수 있는 함수 호출을 해서 전달...

        // Entity -> ResponseDto
        MemoResponseDto memoResponseDto = new MemoResponseDto(post);
        return memoResponseDto;
    }

    public List<MemoResponseDto> getPost() {
        PostRepository postRepository = new PostRepository(jdbcTemplate);
        return postRepository.findAll();
    }

    public Long updateMemo(Long id, MemoRequestDto requestDto) {
        PostRepository postRepository = new PostRepository(jdbcTemplate);
        Post post = postRepository.findById(id);
        if(post != null) {
            // memo 내용 수정
            postRepository.update(id, requestDto);
            return id;
        } else {
            throw new IllegalArgumentException("선택한 메모는 존재하지 않습니다.");
        }
    }

    public Long deletePost(Long id) {
        PostRepository postRepository = new PostRepository(jdbcTemplate);
        Post post = postRepository.findById(id);
        if(post != null){
            postRepository.delete(id);
            return id;
        }else{
            throw new IllegalArgumentException("선택한 메모는 존재하지 않습니다.");
        }
    }

    public MemoResponseDto getData(Long id) {
        PostRepository postRepository = new PostRepository(jdbcTemplate);
        Post post = postRepository.findById(id);
        if(post !=null){
            return postRepository.getOne(id);
        }else {
            throw new IllegalArgumentException("선택한 메모는 존재하지 않습니다.");
        }
    }
}
