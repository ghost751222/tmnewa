package com.example.tmnewa.vo.cti;

import lombok.Getter;
import lombok.Setter;

import java.util.LinkedHashMap;
import java.util.Map;


@Getter
public class CTIResponseVo {

    @Setter
    private int ret;
    private Map<String, String> vars = new LinkedHashMap<>();

    public void addVar(String key,String value) {
        if(vars ==null) vars = new LinkedHashMap<>();
        vars.put(key, value);
    }

    @Override
    public String toString() {
        StringBuilder stringBuilder = new StringBuilder(String.format("ret=%d\n", ret));
        for (var entry : vars.entrySet()) {
            stringBuilder.append(String.format("var=%s,\"%s\"\n", entry.getKey(), entry.getValue()));
        }
        return stringBuilder.toString();
    }
}
