package com.example.api_assignment.service;

import com.example.api_assignment.dto.MemoRequestDto;
import com.example.api_assignment.dto.MemoResponseDto;
import com.example.api_assignment.entity.Post;
import com.example.api_assignment.repository.PostRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.NoSuchElementException;
@Service
public class PostService {
    private final PostRepository postRepository;

    public PostService(PostRepository postRepository) {
        this.postRepository = postRepository;
    }


    public MemoResponseDto createPost(MemoRequestDto requestDto) {
        Post post = new Post(requestDto);

        //Post savePost =  postRepository.save(post);//postrepository에 디비를 저장할 수 있는 함수 호출을 해서 전달...
        postRepository.save(post);//postrepository에 디비를 저장할 수 있는 함수 호출을 해서 전달...

        // Entity -> ResponseDto
        MemoResponseDto memoResponseDto = new MemoResponseDto(post);
        return memoResponseDto;
    }

    public List<MemoResponseDto> getPost() {

        return postRepository.findAll().stream().map(MemoResponseDto::new).toList();
    }

    @Transactional
    public Long updateMemo(Long id, MemoRequestDto requestDto) {

        Post post = findPost(id);



        post.update(requestDto);
        return id;

    }

    public Long deletePost(Long id, String password) {
        Post post = findPost(id);
        if(!post.getPassword().equals(password)){
            throw new IllegalArgumentException("비밀번호 불일치");
        }

        postRepository.delete(post);
        return id;

    }
    @Transactional(readOnly = true)
    public MemoResponseDto getData(Long id) {
        Post post = postRepository.findById(id).orElseThrow(() -> new NoSuchElementException("해당ID의 게시글이 없습니다."));
        return new MemoResponseDto(post);
    }


    public Post findPost(Long id){
        return postRepository.findById(id).orElseThrow(() ->
                new IllegalArgumentException("선택한 포스트는 존재 안함")
        );
    }
}
