package com.wdl.web.controller;

import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;

@Controller
public class WebSocketController {

    @MessageMapping("/updateProgress")
    @SendTo("/topic/progress")
    public String updateProgress(String progressMessage) {
        return progressMessage;
    }
}