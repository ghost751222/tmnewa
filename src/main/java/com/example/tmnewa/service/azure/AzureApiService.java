package com.example.tmnewa.service.azure;


import com.example.tmnewa.config.TWNEWAConfigProperties;
import com.example.tmnewa.utils.HttpClientUtils;
import com.example.tmnewa.utils.JacksonUtils;
import com.example.tmnewa.vo.azure.AzureResponseVo;
import lombok.extern.slf4j.Slf4j;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.tomcat.util.http.fileupload.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.io.InputStream;
import java.net.URI;
import java.nio.charset.StandardCharsets;
import java.security.KeyManagementException;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;

@Service
@Slf4j
public class AzureApiService {

    @Autowired
    TWNEWAConfigProperties twnewaConfigProperties;

    public AzureResponseVo me(String token) throws KeyStoreException, NoSuchAlgorithmException, IOException, KeyManagementException {
        InputStream is = null;
        String content;
        AzureResponseVo requestQueryVo = null;
        try {

            HttpClient client = HttpClientUtils.getHttpClient();

            String url = URI.create("https://graph.microsoft.com/v1.0/me").toString();
            var method = HttpClientUtils.getHttpGetMethod(url);
            method.addHeader("authorization:","Bearer " + token);
            HttpResponse response = client.execute(method);
            is = response.getEntity().getContent();
            content = new String(is.readAllBytes(), StandardCharsets.UTF_8);
            log.info(content);
            requestQueryVo = JacksonUtils.readValue(content, AzureResponseVo.class);
        } catch (Exception e) {
            throw e;
        } finally {
            IOUtils.closeQuietly(is);
        }
        return requestQueryVo;
    }
    public AzureResponseVo accessToken(String code) throws KeyStoreException, NoSuchAlgorithmException, IOException, KeyManagementException {

        InputStream is = null;
        String content;
        AzureResponseVo requestQueryVo = null;
        try {

            HttpClient client = HttpClientUtils.getHttpClient();

            String url = "https://graph.microsoft.com/v1.0/me";
            url = "https://login.microsoftonline.com/" + twnewaConfigProperties.getAzureTenantId() + "/oauth2/v2.0/token?";
            url = URI.create(url).toString();

            var formData = "client_id=" + twnewaConfigProperties.getAzureClientId();
            formData += "&code=" + code;
            formData += "&scope=profile openid email";
            formData += "&grant_type=authorization_code";
            formData += "&redirect_uri=" + twnewaConfigProperties.getAzure_redirect_uri();
            formData += "&client_secret=" + twnewaConfigProperties.getAzureClientSecret();

            HttpPost post = HttpClientUtils.getHttpPostMethod(url);
            HttpClientUtils.addHeaderContentFormUrlEncoded(post);

            StringEntity entity = new StringEntity(formData);
            post.setEntity(entity);
            HttpResponse response = client.execute(post);

            is = response.getEntity().getContent();
            content = new String(is.readAllBytes(), StandardCharsets.UTF_8);
            requestQueryVo = JacksonUtils.readValue(content, AzureResponseVo.class);
        } catch (Exception e) {
            throw e;
        } finally {
            IOUtils.closeQuietly(is);
        }
        return requestQueryVo;

    }

}
