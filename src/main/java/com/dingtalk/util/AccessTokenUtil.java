package com.dingtalk.util;

import com.dingtalk.api.DefaultDingTalkClient;
import com.dingtalk.api.request.OapiGettokenRequest;
import com.dingtalk.api.response.OapiGettokenResponse;
import com.dingtalk.constant.CommonConstant;
import com.dingtalk.constant.UrlConstant;
import com.taobao.api.ApiException;

/**
 * 获取access_token工具类
 */
public class AccessTokenUtil {

    public static String getAccessToken() throws ApiException {
        DefaultDingTalkClient client = new DefaultDingTalkClient(UrlConstant.GET_ACCESS_TOKEN_URL);
        OapiGettokenRequest request = new OapiGettokenRequest();

        request.setAppkey(CommonConstant.APP_KEY);
        request.setAppsecret(CommonConstant.APP_SECRET);
        request.setHttpMethod("GET");
        OapiGettokenResponse response = client.execute(request);

        return response.getAccessToken();
    }
}
