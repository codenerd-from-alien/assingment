package com.example.api_assignment.entity;

import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class Timestamped {
    LocalDateTime now = LocalDateTime.now();
    private LocalDateTime createdAt = now;
    private LocalDateTime modifiedAt = now;
}