package com.example.api_assignment.entity;


import com.example.api_assignment.dto.MemoRequestDto;

import com.example.api_assignment.dto.MemoRequestDto;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@Getter
@Setter
@NoArgsConstructor
public class Post extends Timestamped{

    private Long id;
    private String username;
    private String contents;
    private String title;
    private String password;

    public Post(MemoRequestDto requestDto){
        this.username = requestDto.getUsername();
        this.contents = requestDto.getContents();
        this.title = requestDto.getTitle();
        this.password = requestDto.getPassword();
    }

    public void update(MemoRequestDto requestDto) {
        this.username = requestDto.getUsername();
        this.contents = requestDto.getContents();
        this.title = requestDto.getTitle();
        this.password =requestDto.getPassword();
    }
}
