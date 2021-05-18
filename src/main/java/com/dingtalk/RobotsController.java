package com.dingtalk;

import com.alibaba.fastjson.JSONObject;
import com.dingtalk.api.DefaultDingTalkClient;
import com.dingtalk.api.DingTalkClient;
import com.dingtalk.api.request.OapiImChatScencegroupMessageSendV2Request;
import com.dingtalk.api.response.OapiImChatScencegroupMessageSendV2Response;
import com.dingtalk.constant.AppConstant;
import com.dingtalk.constant.UrlConstant;
import com.dingtalk.util.AccessTokenUtil;
import com.taobao.api.ApiException;
import org.springframework.web.bind.annotation.*;

/**
 * 实现了机器人的简单问答功能
 */
@RestController
public class RobotsController {

    private static final String cardMSG = "测试卡片消息";

    @RequestMapping(value = "/robots")
    public String helloRobots(@RequestBody(required = false) JSONObject json
    ) {
        System.out.println(json);
        String content = json.getJSONObject("text").get("content").toString().replaceAll(" ", "");
        if (cardMSG.contains(content)) {
            return actionCardMsg();
        }
        return learning();
    }

    /**
     * 直接回复文本消息
     */
    private String learning() {

        return "{" +
            "    \"text\": {" +
            "        \"content\": \"我就是我, 是不一样的烟火\"" +
            "    }," +
            "    \"msgtype\": \"text\"" +
            "}";
    }

    /**
     * 调用发送群助手消息接口发送卡片消息
     */
    public String actionCardMsg() {
        try {
            String accessToken = AccessTokenUtil.getAccessToken();
            DingTalkClient client = new DefaultDingTalkClient(UrlConstant.SCENCEGROUP_MESSAGE_SEND_V2);
            OapiImChatScencegroupMessageSendV2Request req = new OapiImChatScencegroupMessageSendV2Request();
            req.setTargetOpenConversationId(AppConstant.OPEN_CONVERSATION_ID);
            // 这个消息模板ID是 官方通用测试模板
            req.setMsgTemplateId("offical_template_test_action_card");
            req.setMsgParamMapString("{     " +
                "\"title\": \"会话列表显示的标题\"," +
                "\"markdown\": \"# 消息正文标题\"," +
                "\"btn_orientation\": \"2\"," +
                "\"btn_title_1\": \"按钮一号\"," +
                "\"action_url_1\": \"www.dingtalk.com\"," +
                "\"btn_title_2\": \"按钮二号\"," +
                "\"action_url_2\": \"www.dingtalk.com\"}");
            req.setRobotCode(AppConstant.ROBOT_CODE);
            OapiImChatScencegroupMessageSendV2Response rsp = client.execute(req, accessToken);
            System.out.println(rsp.getBody());
            return rsp.getBody();
        } catch (ApiException e) {
            e.printStackTrace();
            throw new RuntimeException();
        }
    }
}
