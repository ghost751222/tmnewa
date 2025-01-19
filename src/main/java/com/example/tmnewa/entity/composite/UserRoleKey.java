package com.example.tmnewa.entity.composite;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.Data;

import java.io.Serializable;

@Data
@Embeddable
public class UserRoleKey implements Serializable {
    @Column(name = "user_id")
    private Long user_id;

    @Column(name = "role_id")
    private Long role_id;
}
