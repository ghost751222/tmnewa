package com.example.tmnewa.entity.qa;

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;

@Entity
@Table(name = "qa_task_job")
@Data
public class QaTaskJob {

    @Id
    private String  call_id;
    private LocalDateTime call_start_time;
    private LocalDateTime call_stop_time;
    private String call_agent_id;
    private String call_customer_id;
    private String call_ext_no;

    @Column(updatable = false)
    private Long creator;
    @Column(updatable = false)
    private LocalDateTime createdAt;

    private Long updater;
    private LocalDateTime updatedAt;
}
