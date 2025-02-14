package com.example.tmnewa.config;


import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;


@Data
@Configuration
@ConfigurationProperties(prefix = "tmnewa")
public class TWNEWAConfigProperties {

    private String crossSiteUrl;
    private boolean isAD;
    private String adDomain;
    private String adLdap;
    private String adBase;

}
