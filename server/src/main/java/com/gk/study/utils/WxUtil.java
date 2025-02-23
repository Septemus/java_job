package com.gk.study.utils;

import cn.hutool.http.HttpResponse;
import cn.hutool.http.HttpUtil;
import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONUtil;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;
import java.net.URI;
import java.util.HashMap;
import java.util.Map;

/**
 * @author joe
 */
@Component
@Data
@ConfigurationProperties(prefix = "wechat")
public class WxUtil {

    /**
     * The appid of WeChat App
     */
    private String appId;
    /**
     * The app secret of WeChat App
     */
    private String appSecret;

    /**
     * Address of WeChat API
     */
    private String WxAPI;
    /**
     * Return authorization code from api
     */
    private static final String GRANT_TYPE="authorization_code";

    @Autowired
    private RestTemplate rest;

    public String getOpenIdByTempCode(String tmpCode) {
        MultiValueMap<String, String> urlVariables = new LinkedMultiValueMap<>();
        urlVariables.add("appid", appId);
        urlVariables.add("secret", appSecret);
        urlVariables.add("js_code", tmpCode);
        urlVariables.add("grant_type", GRANT_TYPE);
        String url = UriComponentsBuilder
                .fromHttpUrl(WxAPI)
                .queryParams(urlVariables)
                .toUriString();
        HttpResponse response = HttpUtil.createGet(url).execute();

        // 获取 session_key 和 openid
        JSONObject parseObj = JSONUtil.parseObj(response.body());

        String openid = (String) parseObj.get("openid");

        return openid;
    }

}
