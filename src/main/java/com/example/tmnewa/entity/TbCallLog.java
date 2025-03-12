package com.example.tmnewa.entity;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class TbCallLog {

    private LocalDateTime f_start_time;
    private LocalDateTime f_stop_time;
    private String  f_call_id;
    private String f_agent_id;
    private String f_customer_id;
    private String f_ext_no;

}
