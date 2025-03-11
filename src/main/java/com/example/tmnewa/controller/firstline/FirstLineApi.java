package com.example.tmnewa.controller.firstline;

import com.example.tmnewa.config.TWNEWAConfigProperties;
import com.example.tmnewa.utils.HttpClientUtils;
import com.example.tmnewa.utils.JacksonUtils;
import com.example.tmnewa.vo.firstline.FirstLineApiResponseVo;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.StringEntity;
import org.apache.http.message.BasicHeader;
import org.apache.http.protocol.HTTP;
import org.apache.tomcat.util.http.fileupload.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.security.KeyManagementException;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.text.MessageFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@Slf4j
public class FirstLineApi {


    TWNEWAConfigProperties twnewaConfigProperties;

    @Autowired
    public FirstLineApi(TWNEWAConfigProperties twnewaConfigProperties) {
        this.twnewaConfigProperties = twnewaConfigProperties;
    }

    public FirstLineApiResponseVo getInteractCollectionById(LocalDateTime start, LocalDateTime end, Integer id) throws IOException, NoSuchAlgorithmException, KeyStoreException, KeyManagementException {
        String token = this.accessToken();
        FirstLineApiResponseVo firstLineApiResponseVo = null;
        InputStream is = null;
        try {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyyMMdd");
            HttpClient client = HttpClientUtils.getHttpClient();

            String url = twnewaConfigProperties.getFirstLineUrl() + "/api/v1/interact-collection" +
                    new MessageFormat("/{0}?created_at__start={1}&created_at__end={2}").format(new Object[]{id, start.format(formatter), end.format(formatter)});

            HttpClientUtils.HttpGet get = HttpClientUtils.getHttpGetMethod(url);
            get.addHeader(new BasicHeader("Authorization", "Bearer " + token));
            HttpResponse response = client.execute(get);

            is = response.getEntity().getContent();
            String content = new String(is.readAllBytes(), StandardCharsets.UTF_8);
            JsonNode rootNode = JacksonUtils.readValue(content,JsonNode.class);
            JsonNode dataNode = rootNode.get("data");
            firstLineApiResponseVo = JacksonUtils.readValue(dataNode.toString(), FirstLineApiResponseVo.class);
        } catch (Exception e) {
            throw e;
        } finally {
            IOUtils.closeQuietly(is);
        }
        return firstLineApiResponseVo;
    }


    public List<FirstLineApiResponseVo> getInteractCollection(LocalDateTime start, LocalDateTime end) throws IOException, NoSuchAlgorithmException, KeyStoreException, KeyManagementException {
        String token = this.accessToken();
        InputStream is = null;
        try {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyyMMdd");
            HttpClient client = HttpClientUtils.getHttpClient();

            String url = twnewaConfigProperties.getFirstLineUrl() + "/api/v1/interact-collection?" +
                    new MessageFormat("created_at__start={0}&created_at__end={1}").format(new Object[]{start.format(formatter), end.format(formatter)});

            HttpClientUtils.HttpGet get = HttpClientUtils.getHttpGetMethod(url);
            get.addHeader(new BasicHeader("Authorization", "Bearer " + token));
            HttpResponse response = client.execute(get);

            is = response.getEntity().getContent();
            String content = new String(is.readAllBytes(), StandardCharsets.UTF_8);
            JsonNode rootNode = JacksonUtils.readValue(content,JsonNode.class);
            JsonNode dataNode = rootNode.get("data");

           return JacksonUtils.readValue(dataNode.toString(), new TypeReference<>() {});
        } catch (Exception e) {
            throw e;
        } finally {
            IOUtils.closeQuietly(is);
        }
    }

    public String accessToken() throws KeyStoreException, NoSuchAlgorithmException, IOException, KeyManagementException {
        FirstLineApiResponseVo firstLineApiResponseVo = null;
        InputStream is = null;
        try {
            HttpClient client = HttpClientUtils.getHttpClient();

            String url = twnewaConfigProperties.getFirstLineUrl() + "/api/v1/auth";


            HttpPost post = HttpClientUtils.getHttpPostMethod(url);
            HttpClientUtils.addHeaderContentJson(post);
            Map<String, String> params = new HashMap<String, String>();
            params.put("name", twnewaConfigProperties.getFirstLineUserName());
            params.put("password", twnewaConfigProperties.getFirstLinePassword());
            StringEntity entity = new StringEntity(JacksonUtils.writeValueAsString(params));
            entity.setContentType(new BasicHeader(HTTP.CONTENT_TYPE, ContentType.APPLICATION_JSON.getMimeType()));
            post.setEntity(entity);

            HttpResponse response = client.execute(post);

            is = response.getEntity().getContent();
            String content = new String(is.readAllBytes(), StandardCharsets.UTF_8);
            JsonNode rootNode = JacksonUtils.readValue(content,JsonNode.class);
            JsonNode dataNode = rootNode.get("data");
            firstLineApiResponseVo = JacksonUtils.readValue(dataNode.toString(), FirstLineApiResponseVo.class);
            return firstLineApiResponseVo.getToken();
        } catch (Exception e) {
            throw e;
        } finally {
            IOUtils.closeQuietly(is);
        }


    }


}
