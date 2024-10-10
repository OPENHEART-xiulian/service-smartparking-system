package com.sp.cjgc.backend.utils;

import cn.hutool.json.JSONUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import javax.annotation.PostConstruct;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.util.Map;

/**
 * @Author: Zoey
 * @Date: 2023/9/20
 * @Time: 下午3:22
 * @Describe: http请求工具类
 */
@Slf4j
@Component
public class HttpRequestUtil {

    private static RestTemplate restTemplateConfig;

    private RestTemplate restTemplate;

    public HttpRequestUtil(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    @PostConstruct
    private void init() {
        restTemplateConfig = restTemplate;
    }

    /**
     * 以POST 方式调用
     *
     * @param reqName
     * @param url
     * @param httpEntity
     * @return
     */
    public static Object httpPost(String reqName, String url, HttpEntity<Map<String, Object>> httpEntity) {
        log.info("-------以 POST请求方式 调用 {} 方法开始-------", reqName);
        log.info("调用地址url: {}", url);
        log.info("请求参数: {}", JSONUtil.toJsonPrettyStr(httpEntity.getBody()));
        try {
            ResponseEntity<Object> response = restTemplateConfig.postForEntity(url, httpEntity, Object.class);
            log.info("以 POST请求方式 调用 {} 方法获取到的数据: {}", reqName, JSONUtil.toJsonPrettyStr(response.getBody()));
            log.info("-------以 POST请求方式 调用 {} 方法结束-------", reqName);
            return response.getBody();
        } catch (Exception e) {
            log.error("远程调用 {} 失败，失败原因: {}", reqName, e.getMessage());
            throw e;
        }
    }

    /**
     * 以GET 方式调用
     *
     * @param reqName
     * @param url
     * @return
     */
    public static String httpGet(String reqName, String url) {
        log.info("-------以 GET请求方式 调用 {} 方法开始-------", reqName);
        log.info("调用地址url: {}", url);
        try {
            ResponseEntity<String> response = restTemplateConfig.getForEntity(url, String.class);
            log.info("以 GET 请求方式 调用 {} 方法获取到的数据: {}", reqName, JSONUtil.toJsonPrettyStr(response.getBody()));
            log.info("-------以 GET请求方式 调用 {} 方法结束-------", reqName);
            return response.getBody();
        } catch (Exception e) {
            log.error("远程调用 {} 失败，失败原因: {}", reqName, e.getMessage());
            throw e;
        }
    }

    /**
     * 发送请求
     *
     * @param serverUrl 请求地址
     */
    public static String getResponse(String serverUrl) {
        log.info("请求地址: {}", serverUrl);
        // 用JAVA发起http请求，并返回json格式的结果
        StringBuffer result = new StringBuffer();
        try {
            URL url = new URL(serverUrl);
            URLConnection conn = url.openConnection();
            BufferedReader in = new BufferedReader(new InputStreamReader(conn.getInputStream()));
            String line;
            while ((line = in.readLine()) != null) {
                result.append(line);
            }
            in.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result.toString();
    }
}
