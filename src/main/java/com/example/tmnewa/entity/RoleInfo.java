package com.example.tmnewa.entity;

import jakarta.persistence.*;
import jdk.jfr.Enabled;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "role_info")
@Data
public class RoleInfo {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(columnDefinition = "NVARCHAR(255)")
    private String name;

    @Column(updatable = false)
    private LocalDateTime createdAt;

    private LocalDateTime updatedAt;


    @ManyToMany(mappedBy = "roleInfos")
    private Set<UserInfo> userInfos;  // 初始化 users 集合
}
