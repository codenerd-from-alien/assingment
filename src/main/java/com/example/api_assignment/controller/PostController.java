package com.example.api_assignment.controller;

import com.example.api_assignment.dto.MemoRequestDto;
import com.example.api_assignment.dto.MemoResponseDto;
import com.example.api_assignment.service.PostService;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
public class PostController {

    private final JdbcTemplate jdbcTemplate;

    public PostController(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }
    @PostMapping("/post")
    public MemoResponseDto createPost(@RequestBody MemoRequestDto requestDto){
        //컨트롤러에서 해당 서비스 클래스의 함수를 호출하기 위해서 포스트 서비스 객체를 생성한다.
        PostService postService = new PostService(jdbcTemplate); //jdbcTemplate을 넣어줘야 sql db 저장 로직 사용가능
        return postService.createPost(requestDto);// 클라이언트로 보내기
    }

    @GetMapping("/posts")
    public List<MemoResponseDto> getPost(){//Post서비스 클래스에도 jdbc 템플릿을 주고
        PostService postService = new PostService(jdbcTemplate);
        return postService.getPost();//서비스 패키지으로 부터 저장한 디비를 받는 로직을 써야 하므로 이렇게 작성
    }

    @PutMapping("/post/{id}")
    public Long updatePost(@PathVariable Long id, @RequestBody MemoRequestDto requestDto ){
        PostService postService = new PostService(jdbcTemplate);
        return postService.updateMemo(id, requestDto);
    }

    @GetMapping("/post/{id}")
    public MemoResponseDto getData(@PathVariable Long id, @RequestBody MemoRequestDto requestDto){
        PostService postService = new PostService(jdbcTemplate);
        return postService.getData(id);
    }
    @DeleteMapping("/post/{id}")
    public Long deletePost(@PathVariable Long id,@RequestBody MemoRequestDto requestDto ){
        PostService postService = new PostService(jdbcTemplate);
        return postService.deletePost(id);

    }
}
