package com.example.api_assignment.controller;

import com.example.api_assignment.dto.MemoRequestDto;
import com.example.api_assignment.dto.MemoResponseDto;
import com.example.api_assignment.service.PostService;

import lombok.RequiredArgsConstructor;

import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api")
public class PostController {

    private final PostService postService;

    @PostMapping("/post")
    public MemoResponseDto createPost(@RequestBody MemoRequestDto requestDto){
        //컨트롤러에서 해당 서비스 클래스의 함수를 호출하기 위해서 포스트 서비스 객체를 생성한다.
        return postService.createPost(requestDto);// 클라이언트로 보내기
    }

    @GetMapping("/posts")
    public List<MemoResponseDto> getPost(){//Post서비스 클래스에도 jdbc 템플릿을 주고
        return postService.getPost();//서비스 패키지으로 부터 저장한 디비를 받는 로직을 써야 하므로 이렇게 작성
    }

    @PutMapping("/post/{id}")
    public Long updatePost(@PathVariable Long id, @RequestBody MemoRequestDto requestDto ){
        return postService.updateMemo(id, requestDto);
    }

    @GetMapping("/post/{id}")
    public MemoResponseDto getData(@PathVariable Long id, @RequestBody MemoRequestDto requestDto){
        return postService.getData(id);
    }
    @DeleteMapping("/post/{id}")
    public Long deletePost(@PathVariable Long id,@RequestBody MemoRequestDto requestDto ){
        return postService.deletePost(id, requestDto.getPassword());
    }


}
