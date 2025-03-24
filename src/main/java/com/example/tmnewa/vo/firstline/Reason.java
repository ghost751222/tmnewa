package com.example.tmnewa.vo.firstline;

import lombok.Data;

@Data
public class Reason {
    private Integer id;
    private String name;
    boolean is_active;
    private String description;
    private Integer category_id;
    private Category category;
}
