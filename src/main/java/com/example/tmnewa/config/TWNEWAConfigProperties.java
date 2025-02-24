package com.example.tmnewa.config;


import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;


@Data
@Configuration
@ConfigurationProperties(prefix = "tmnewa")
public class TWNEWAConfigProperties {

    private String azureTenantId;
    private String azureClientId;
    private String azureClientSecret;
    private String azure_redirect_uri;
    private String crossSiteUrl;
    private boolean isAD;
    private String adDomain;
    private String adLdap;
    private String adBase;

    public String getAzureUrl(){

        return String.format("https://login.microsoftonline.com/%s/oauth2/v2.0/authorize?response_type=code id_token&client_id=%S&scope=openid profile offline_access&state=Lv6v6Xuo7YvtmyLKnzz6wIA4ZIKH0_Xk2CHcKrRSpow=&redirect_uri=%s&nonce=fcmiHXCbStjcX3qBtJ9HKiROMf1B3gMknZhKJlb0T-U&response_mode=form_post",azureTenantId,azureClientId,azure_redirect_uri);
    }

}
