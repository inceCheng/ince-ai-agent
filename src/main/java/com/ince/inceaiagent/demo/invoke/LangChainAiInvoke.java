package com.ince.inceaiagent.demo.invoke;

import dev.langchain4j.community.model.dashscope.QwenChatModel;
import dev.langchain4j.model.chat.ChatLanguageModel;

public class LangChainAiInvoke {
    public static final String API_KEY = "your-api-key";

    public static void main(String[] args) {
        ChatLanguageModel qwenModel = QwenChatModel.builder()
                .apiKey(API_KEY)
                .modelName("qwen-max")
                .build();
        String answer = qwenModel.chat("你是谁");
        System.out.println(answer);
    }
}
