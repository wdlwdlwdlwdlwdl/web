package com.wdl.web.service.impl;

import com.wdl.web.service.ProgressUpdateService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;

@Service
public class ProgressUpdateServiceImpl implements ProgressUpdateService {
    @Autowired
    private SimpMessagingTemplate messagingTemplate;

    @Override
    public void sendProgressUpdate(String progressMessage) {
        messagingTemplate.convertAndSend("/topic/progress", progressMessage);
    }
}
