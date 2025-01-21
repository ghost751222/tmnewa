package com.example.tmnewa.entity.qa;


import jakarta.persistence.*;
import lombok.Data;
import org.hibernate.annotations.Nationalized;

import java.time.LocalDateTime;

@Entity
@Table(name = "qa_design_template")
@Data
public class QADesignTemplate {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Nationalized
    @Column(name = "name", nullable = false, length = 512)
    private String name;

    @Nationalized
    @Column(name = "product", length = 1024)
    private String product;

    @Column(updatable = false)
    private Long creator;

    @Column(updatable = false)
    private LocalDateTime createdAt;

    private Long updater;
    private LocalDateTime updatedAt;
}
