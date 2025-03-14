package com.example.tmnewa.entity.qa;

import jakarta.persistence.*;
import lombok.Data;
import org.hibernate.annotations.Nationalized;

import java.time.LocalDateTime;

@Entity
@Table(name = "qa_task_job")
@Data
public class QaTaskJob {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String  call_id;
    @Column(nullable = false)
    private LocalDateTime call_start_time;
    @Column(nullable = false)
    private LocalDateTime call_stop_time;
    private String call_agent_id;
    private String call_customer_id;
    private String call_ext_no;
    private LocalDateTime qa_time;
    @Column(nullable = false)
    private String status;
    @Nationalized
    private String product_name;
    @Column(updatable = false)
    private Long creator;
    @Column(updatable = false)
    private LocalDateTime createdAt;

    private Long updater;
    private LocalDateTime updatedAt;
}
