package com.example.tmnewa.entity;



import jakarta.persistence.*;
import lombok.Data;
import java.time.LocalDateTime;
import java.util.Set;

@Table(name = "user_info")
@Entity
@Data
public class UserInfo {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true)
    private String account;

    private String password;
    @Column(columnDefinition = "NVARCHAR(255)")
    private String name;

    @Column(updatable = false)
    private LocalDateTime createdAt;

    private LocalDateTime updatedAt;


    @ManyToMany(fetch = FetchType.LAZY)  // EAGER 加載
    @JoinTable(
            name = "user_roles",  // 這裡指定關聯表名稱
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "role_id")
    )
    private Set<RoleInfo> roleInfos;  // 初始化 roles 集合

}
