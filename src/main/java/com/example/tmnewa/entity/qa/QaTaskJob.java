package com.example.tmnewa.entity.qa;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.*;
import lombok.Data;
import org.hibernate.annotations.Comment;
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
    @Comment("通話ID")
    private String  call_id;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @Column(nullable = false)
    @Comment("開始時間")
    private LocalDateTime call_start_time;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @Column(nullable = false)
    @Comment("結束時間")
    private LocalDateTime call_stop_time;

    @Comment("來源")
    private String call_type;

    @Comment("專員ID")
    private String call_agent_id;

    @Comment("客戶ID")
    private String call_customer_id;

    @Comment("專員分機")
    private String call_ext_no;


    @Comment("質檢員ID")
    @Column(name = "qa_id")
    private Long qaId;


    @Comment("質檢員名稱")
    @Column(name = "qa_name")
    private String qaName;


    @Comment("質檢時間")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @Column(name = "qa_time")
    private LocalDateTime qaTime;

    @Comment("服務原因")
    private String service_reason;

    @Comment("質檢分數總分")
    @Column(name = "score_total")
    private Integer scoreTotal;

    @Comment("分數總分")
    @Column(name = "supervisor_score_total")
    private Integer supervisorScoreTotal;


    @Comment("質檢狀態")
    @Column(nullable = false)
    private String status;

    @Comment("險別")
    @Nationalized
    private String product_name;

    @Comment("模板ID")
    private Long templateId;

    @Column(updatable = false)
    private Long creator;
    @Column(updatable = false)
    private LocalDateTime createdAt;

    private Long updater;
    private LocalDateTime updatedAt;
}
