package com.ince.inceaiagent.service;

import cn.hutool.core.util.StrUtil;
import cn.hutool.http.HttpRequest;
import cn.hutool.http.HttpResponse;
import cn.hutool.json.JSONUtil;
import com.ince.inceaiagent.demo.invoke.DashScopeConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class DashScopeHttpService {
    private static final String DASHSCOPE_API_URL = "https://dashscope.aliyuncs.com/api/v1/services/aigc/text-generation/generation";
    private final DashScopeConfig dashScopeConfig;

    @Autowired
    public DashScopeHttpService(DashScopeConfig dashScopeConfig) {
        this.dashScopeConfig = dashScopeConfig;
    }

    public String callWithMessage(String userMessage) {
        try {
            // 构建请求体
            Map<String, Object> requestBody = new HashMap<>();
            requestBody.put("model", "qwen-plus");
            
            // 构建输入对象
            Map<String, Object> input = new HashMap<>();
            
            // 构建消息列表
            List<Map<String, String>> messages = new ArrayList<>();
            
            // 添加系统消息
            Map<String, String> systemMsg = new HashMap<>();
            systemMsg.put("role", "system");
            systemMsg.put("content", "You are a helpful assistant.");
            messages.add(systemMsg);
            
            // 添加用户消息
            String defaultNullMessage = "\"\"";
            Map<String, String> userMsg = new HashMap<>();
            userMsg.put("role", "user");
            userMsg.put("content", defaultNullMessage.equals(userMessage) ? "你是谁" : userMessage);
            messages.add(userMsg);
            
            input.put("messages", messages);
            requestBody.put("input", input);
            
            // 添加参数
            Map<String, Object> parameters = new HashMap<>();
            parameters.put("result_format", "message");
            requestBody.put("parameters", parameters);

            // 发送请求
            HttpResponse response = HttpRequest.post(DASHSCOPE_API_URL)
                    .header("Authorization", "Bearer " + dashScopeConfig.getApiKey())
                    .header("Content-Type", "application/json")
                    .body(JSONUtil.toJsonStr(requestBody))
                    .execute();

            if (response.isOk()) {
                return response.body();
            } else {
                return "Error: " + response.body();
            }
        } catch (Exception e) {
            return "Error: " + e.getMessage();
        }
    }
} 