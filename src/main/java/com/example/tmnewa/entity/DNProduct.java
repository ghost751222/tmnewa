package com.example.tmnewa.entity;


import jakarta.persistence.*;
import lombok.Data;
import org.hibernate.annotations.Nationalized;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

@Data
@Table(name = "dn_product")
@Entity
public class DNProduct {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "dn")
    private String dn;

    @Column(name = "product_name")
    private String productName;

}
