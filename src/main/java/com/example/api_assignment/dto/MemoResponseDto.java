package com.example.api_assignment.dto;


import com.example.api_assignment.entity.Post;

import com.example.api_assignment.entity.Post;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class MemoResponseDto {
    private Long id;
    private String username;
    private String contents;
    private String title;
    private String password;
    private LocalDateTime createAt;
    private LocalDateTime modifiedAt;
    public MemoResponseDto(Post post) {
        this.id = post.getId();
        this.username = post.getUsername();
        this.contents = post.getContents();
        this.createAt = post.getCreatedAt();
        this.modifiedAt = post.getModifiedAt();
        this.title = post.getTitle();
        this.password = post.getPassword();
    }
}