package com.example.tmnewa.entity;


import jakarta.persistence.*;
import lombok.Data;
import org.hibernate.annotations.Nationalized;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

@Data
@Table(name = "db_routine")
@Entity
public class DNRoutine {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;


    private LocalDate startDate;
    private LocalTime startTime;

    private LocalDate endDate;
    private LocalTime endTime;

    @Enumerated(EnumType.STRING)
    @Column(name = "day_of_week")
    @Nationalized
    private DayOfWeek dayOfWeek; // 若為每週某天則填入

    private String dn;

    @Column(updatable = false)
    private Long creator;
    @Column(updatable = false)
    private LocalDateTime createdAt;
    private Long updater;
    private LocalDateTime updatedAt;


}
