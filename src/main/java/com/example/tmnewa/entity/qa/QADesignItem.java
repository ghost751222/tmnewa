package com.example.tmnewa.entity.qa;

import jakarta.persistence.*;
import lombok.Data;
import org.hibernate.annotations.Nationalized;

import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name = "qa_design_item")
@Data
public class QADesignItem {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Nationalized
    @Column(name = "name", nullable = false, length = 512)
    private String name;

    @Column(name = "score")
    private int score;


    private Integer seq;


    @Column(name = "parent_id")
    private Long parentId;


    @JoinColumn(name = "qa_template_id", nullable = false)
    private Long qaTemplateId;

    @Column(updatable = false)
    private Long creator;
    @Column(updatable = false)
    private LocalDateTime createdAt;

    private Long updater;
    private LocalDateTime updatedAt;

    @Transient
    private List<QADesignItem> children;
}
