package com.example.tmnewa.entity.qa;

import jakarta.persistence.*;
import lombok.Data;
import org.hibernate.annotations.Nationalized;

import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name = "qa_inspection")
@Data
public class QAInspection {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Nationalized
    @Column(name = "name", nullable = false, length = 512)
    private String name;

    private Integer seq;

    @Column(name = "qa_design_item_id")
    private Long qaDesignItemId;

    @Column(name = "qa_design_item_parent_id")
    private Long qaDesignItemParentId;

    @Column(name = "job_id")
    private Long jobId;

    @Column(name = "default_score")
    private int defaultScore;

    @Column(name = "score",updatable = false)
    private int score;

    @Nationalized
    @Column(length = 8192)
    private String notes;

    @Column(name = "supervisor_score")
    private Float supervisorScore;

    @Nationalized
    @Column(name = "supervisor_notes",length = 8192)
    private String supervisorNotes;

    @Column(updatable = false)
    private Long creator;
    @Column(updatable = false)
    private LocalDateTime createdAt;

    private Long updater;
    private LocalDateTime updatedAt;

    @Transient
    private List<QAInspection> children;
}
