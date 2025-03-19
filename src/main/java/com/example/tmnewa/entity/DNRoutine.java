package com.example.tmnewa.entity;


import jakarta.persistence.*;
import lombok.Data;
import org.hibernate.annotations.Nationalized;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

@Data
@Table(name = "dn_routine")
@Entity
public class DNRoutine {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "start_date")
    private LocalDate startDate;

    @Column(name = "start_time",nullable = false)
    private LocalTime startTime;

    @Column(name = "end_date")
    private LocalDate endDate;
    @Column(name = "end_time",nullable = false)
    private LocalTime endTime;


    @Column(name = "day_of_week")
    @Nationalized
    private String dayOfWeek; // 若為每週某天則填入

    @Column(name = "dn",nullable = false)
    private String dn;

    @Column(updatable = false)
    private Long creator;
    @Column(updatable = false)
    private LocalDateTime createdAt;
    private Long updater;
    private LocalDateTime updatedAt;


}
