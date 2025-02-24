package com.example.tmnewa.vo.azure;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

@JsonIgnoreProperties(ignoreUnknown = true)
@Data
public class AzureResponseVo {
        private String token_type;
        private String scope;
        private String access_token;
        private int expires_in;
        private int ext_expires_in;
}
