package com.ince.inceaiagent.service;

import cn.hutool.core.util.StrUtil;
import com.alibaba.dashscope.aigc.generation.Generation;
import com.alibaba.dashscope.aigc.generation.GenerationParam;
import com.alibaba.dashscope.aigc.generation.GenerationResult;
import com.alibaba.dashscope.common.Message;
import com.alibaba.dashscope.common.Role;
import com.alibaba.dashscope.exception.ApiException;
import com.alibaba.dashscope.exception.InputRequiredException;
import com.alibaba.dashscope.exception.NoApiKeyException;
import com.ince.inceaiagent.demo.invoke.DashScopeConfig;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Arrays;

@Slf4j
@Service
public class DashScopeService {
    private final DashScopeConfig dashScopeConfig;

    @Autowired
    public DashScopeService(DashScopeConfig dashScopeConfig) {
        this.dashScopeConfig = dashScopeConfig;
    }

    public GenerationResult callWithMessage(String userMessage) throws ApiException, NoApiKeyException, InputRequiredException {
        Generation gen = new Generation();
        String defaultNullMessage = "\"\"";
        Message systemMsg = Message.builder()
                .role(Role.SYSTEM.getValue())
                .content("You are a helpful assistant.")
                .build();
        Message userMsg = Message.builder()
                .role(Role.USER.getValue())
                .content(defaultNullMessage.equals(userMessage) ? "你是谁" : userMessage)
                .build();
        GenerationParam param = GenerationParam.builder()
                .apiKey(dashScopeConfig.getApiKey())
                .model("qwen-plus")
                .messages(Arrays.asList(systemMsg, userMsg))
                .resultFormat(GenerationParam.ResultFormat.MESSAGE)
                .build();
        return gen.call(param);
    }
} 