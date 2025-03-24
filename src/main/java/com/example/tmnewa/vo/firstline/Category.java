package com.example.tmnewa.vo.firstline;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class Category {
    private Integer id;
    private String name;
    private boolean isEnabled;
    private Integer priority;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
