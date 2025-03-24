package com.example.tmnewa.service.firstline;

import com.example.tmnewa.config.TWNEWAConfigProperties;
import com.example.tmnewa.utils.HttpClientUtils;
import com.example.tmnewa.utils.JacksonUtils;
import com.example.tmnewa.vo.firstline.FirstLineApiResponseVo;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonNode;
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
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@Slf4j
public class FirstLineApiService {


    TWNEWAConfigProperties twnewaConfigProperties;

    private final static DateTimeFormatter DATE_TIME_FORMATTER = DateTimeFormatter.ofPattern("yyyyMMdd");

    @Autowired
    public FirstLineApiService(TWNEWAConfigProperties twnewaConfigProperties) {
        this.twnewaConfigProperties = twnewaConfigProperties;
    }


    public FirstLineApiResponseVo getInteractCollectionById(LocalDate start, LocalDate end, Integer id) throws IOException, NoSuchAlgorithmException, KeyStoreException, KeyManagementException {
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
            JsonNode rootNode = JacksonUtils.readValue(content, JsonNode.class);
            JsonNode dataNode = rootNode.get("data");
            firstLineApiResponseVo = JacksonUtils.readValue(dataNode.toString(), FirstLineApiResponseVo.class);
        } catch (Exception e) {
            throw e;
        } finally {
            IOUtils.closeQuietly(is);
        }
        return firstLineApiResponseVo;
    }


    public List<FirstLineApiResponseVo> getAllInteractCollection(LocalDate start, LocalDate end, int perPage, int page) throws KeyStoreException, NoSuchAlgorithmException, IOException, KeyManagementException {
        String token = this.accessToken();
        InputStream is = null;
        List<FirstLineApiResponseVo> vos = new ArrayList<>();
        try {

            HttpClient client = HttpClientUtils.getHttpClient();

            String url = twnewaConfigProperties.getFirstLineUrl() + "/api/v1/interact-collection?" +
                    new MessageFormat("created_at__start={0}&created_at__end={1}&per_page={2}&page={3}")
                            .format(new Object[]{start.format(DATE_TIME_FORMATTER), end.format(DATE_TIME_FORMATTER), perPage, page});

            HttpClientUtils.HttpGet get = HttpClientUtils.getHttpGetMethod(url);
            get.addHeader(new BasicHeader("Authorization", "Bearer " + token));
            HttpResponse response = client.execute(get);
            is = response.getEntity().getContent();
            String content = new String(is.readAllBytes(), StandardCharsets.UTF_8);
            JsonNode rootNode = JacksonUtils.readValue(content, JsonNode.class);
            JsonNode dataNode = rootNode.get("data");
            JsonNode metaNode = rootNode.get("meta");

            if (metaNode.get("current_page").asInt() == metaNode.get("last_page").asInt()) {
                vos.addAll(JacksonUtils.readValue(dataNode.toString(), new TypeReference<>() {
                }));
            } else {
                vos.addAll(getAllInteractCollection(start, end, perPage, page));
            }

            return vos;
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
            Map<String, String> params = new HashMap<>();
            params.put("name", twnewaConfigProperties.getFirstLineUserName());
            params.put("password", twnewaConfigProperties.getFirstLinePassword());
            StringEntity entity = new StringEntity(JacksonUtils.writeValueAsString(params));
            entity.setContentType(new BasicHeader(HTTP.CONTENT_TYPE, ContentType.APPLICATION_JSON.getMimeType()));
            post.setEntity(entity);

            HttpResponse response = client.execute(post);

            is = response.getEntity().getContent();
            String content = new String(is.readAllBytes(), StandardCharsets.UTF_8);
            JsonNode rootNode = JacksonUtils.readValue(content, JsonNode.class);
            JsonNode dataNode = rootNode.get("data");
            firstLineApiResponseVo = JacksonUtils.readValue(dataNode.toString(), FirstLineApiResponseVo.class);
            return firstLineApiResponseVo.getToken();
        } finally {
            IOUtils.closeQuietly(is);
        }


    }


}
