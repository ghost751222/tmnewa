package com.example.tmnewa.vo.cti;


import jakarta.validation.constraints.NotNull;
import lombok.Data;


@Data
public class Identify {

    @NotNull(message = "身份證號不能為空")
    private int sequence;
    private String letter;
    public Identify(int seq, String letter){
        super();
        this.sequence=seq;
        this.letter=letter;
    }



}
