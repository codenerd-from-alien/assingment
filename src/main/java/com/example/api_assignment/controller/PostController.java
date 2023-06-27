package com.example.api_assignment.controller;


import com.example.api_assignment.dto.MemoRequestDto;
import com.example.api_assignment.dto.MemoResponseDto;
import com.example.api_assignment.entity.Post;
import org.springframework.web.bind.annotation.*;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
@RestController
@RequestMapping("/api")
public class PostController {
    private final Map<Long, Post> postlist = new HashMap<>();
    @PostMapping("/post")
    public MemoResponseDto createPost(@RequestBody MemoRequestDto requestDto){
        Post post = new Post(requestDto);

        Long maxId= postlist.size() > 0 ? Collections.max(postlist.keySet())+1 :1;
        post.setId(maxId);
        postlist.put(post.getId(), post);

        MemoResponseDto memoResponseDto = new MemoResponseDto(post);
        return memoResponseDto;
    }

    @GetMapping("/posts")
    public List<MemoResponseDto> getPost(){
        List<MemoResponseDto> responseList = postlist.values().stream()
                .map(MemoResponseDto::new).toList();
        return responseList;
    }

    @GetMapping("/post/{id}")
    public MemoResponseDto getData(@PathVariable Long id, @RequestBody MemoRequestDto requestDto){
        if(postlist.containsKey(id)){
            Post post = postlist.get(id);
            return new MemoResponseDto(post);
        }else{
            throw new IllegalArgumentException("id값 불일치");
        }
    }
    @PutMapping("/post/{id}")
    public Long updatePost(@PathVariable Long id, @RequestBody MemoRequestDto requestDto ){
        if(postlist.containsKey(id)){
            Post post = postlist.get(id);
            if (post.getPassword().equals(requestDto.getPassword())) {
                post.update(requestDto);
                return id;
            } else {
                throw new IllegalArgumentException("비밀번호가 일치하지 않습니다.");
            }
        }else{
            throw new IllegalArgumentException("존재하지않는 데이터");
        }
    }
    @DeleteMapping("/post/{id}")
    public Long deletePost(@PathVariable Long id,@RequestBody MemoRequestDto requestDto ){
        Post post = postlist.get(id);
        if(postlist.containsKey(id) && post.getPassword().equals(requestDto.getPassword())){
            postlist.remove(id);
            return id;
        }else{
            throw new IllegalArgumentException("메모는 존재하지 않습니다.");
        }
    }





}
