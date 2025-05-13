package com.ince.inceaiagent.controller;

import com.ince.inceaiagent.service.DashScopeHttpService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "DashScope HTTP API", description = "使用 HTTP 客户端调用 DashScope API")
@RestController
@RequestMapping("/api/dashscope/http")
public class DashScopeHttpController {
    private final DashScopeHttpService dashScopeHttpService;

    @Autowired
    public DashScopeHttpController(DashScopeHttpService dashScopeHttpService) {
        this.dashScopeHttpService = dashScopeHttpService;
    }

    @Operation(summary = "使用 HTTP 客户端发送消息到 DashScope")
    @PostMapping("/chat")
    public String chat(@RequestBody String message) {
        return dashScopeHttpService.callWithMessage(message);
    }
} 