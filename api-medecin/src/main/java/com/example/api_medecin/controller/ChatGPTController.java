package com.example.api_medecin.controller;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import com.example.api_medecin.dto.request.ChatGPTRequest;
import com.example.api_medecin.dto.response.ChatGPTResponse;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;


@RestController
@RequestMapping("/bot")
public class ChatGPTController {
    @Value("${openai.api.model}")
    private String model;
    @Value("${openai.api.url}")
    private String apiUrl;

    RestTemplate template;

    ChatGPTController(RestTemplate template) {
        this.template = template;
    }

    @GetMapping("/chat")
    public String chatWithMe(@RequestParam String prompt) {
        ChatGPTRequest request = new ChatGPTRequest(model, prompt);
        ChatGPTResponse response = template.postForObject(apiUrl, request, ChatGPTResponse.class);
        if(response != null){
            return response.getChoices().get(0).getMessage().getContent();
        }
        return new String();
    }

    @GetMapping("/chat/all")
    public ChatGPTResponse chatWithMeResponse(@RequestParam String prompt) {
        ChatGPTRequest request = new ChatGPTRequest(model, prompt);
        ChatGPTResponse response = template.postForObject(apiUrl, request, ChatGPTResponse.class);
        return response;
    }
    
    
}
