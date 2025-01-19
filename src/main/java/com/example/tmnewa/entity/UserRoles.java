package com.example.tmnewa.entity;


import com.example.tmnewa.entity.composite.UserRoleKey;
import jakarta.persistence.*;
import lombok.Data;

@Table(name = "user_roles")
@Entity
@Data
public class UserRoles {

    @EmbeddedId
    private UserRoleKey id;


    @ManyToOne
    @MapsId("user_id")
    @JoinColumn(name = "user_id")
    UserInfo userInfo;

    @ManyToOne
    @MapsId("role_id")
    @JoinColumn(name = "role_id")
    RoleInfo roleInfo;



}
