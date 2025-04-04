package com.example.tmnewa.config;


import lombok.Data;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import java.util.List;


@Data
@Configuration
@ConfigurationProperties(prefix = "tmnewa")
public class TWNEWAConfigProperties {

    private String azureTenantId;
    private String azureClientId;
    private String azureClientSecret;
    private String azurePostLogoutRedirectUri;
    private String crossSiteUrl;
    private boolean isAD;
    private String adDomain;
    private String adLdap;
    private String adBase;

    private String firstLineUrl;
    private String firstLineUserName;
    private String firstLinePassword;

    @Value("${tmnewa.smsIp:smsb2c.mitake.com.tw}")
    private String smsIp;
    @Value("${tmnewa.smsId:crm4527}")
    private String smsId;
    @Value("${tmnewa.smsPassword:ec120823}")
    private String smsPassword;


    @Value("${tmnewa.dnProducts:汽車險,機車險,旅平險,住火險,微電車,傷害險,吉時保}")
    private List<String> dnProducts;
}
