package com.ince.inceaiagent.controller;

import com.alibaba.dashscope.aigc.generation.GenerationResult;
import com.alibaba.dashscope.exception.ApiException;
import com.alibaba.dashscope.exception.InputRequiredException;
import com.alibaba.dashscope.exception.NoApiKeyException;
import com.alibaba.dashscope.utils.JsonUtils;
import com.ince.inceaiagent.service.DashScopeService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "DashScope API", description = "DashScope API 接口")
@RestController
@RequestMapping("/api/dashscope")
public class DashScopeController {

    private final DashScopeService dashScopeService;

    @Autowired
    public DashScopeController(DashScopeService dashScopeService) {
        this.dashScopeService = dashScopeService;
    }

    @Operation(summary = "发送消息到 DashScope")
    @PostMapping("/chat")
    public String chat(@RequestBody String message) {
        try {
            GenerationResult result = dashScopeService.callWithMessage(message);
            return JsonUtils.toJson(result);
        } catch (ApiException | NoApiKeyException | InputRequiredException e) {
            return "Error: " + e.getMessage();
        }
    }
} 