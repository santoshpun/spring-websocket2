package com.santosh.springwebsocket2.controller;


import com.santosh.springwebsocket2.config.WsConfig;
import com.santosh.springwebsocket2.dto.Message;
import com.santosh.springwebsocket2.dto.OutputMessage;
import com.santosh.springwebsocket2.service.UserStore;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;

import java.security.Principal;
import java.text.SimpleDateFormat;
import java.util.Date;

@Slf4j
@Controller
public class WebSocketBroadcastController {
    @Autowired
    private UserStore userStore;
    @Autowired
    private SimpMessagingTemplate messagingTemplate;

    @MessageMapping(WsConfig.ENDPOINT_CONNECT)
    public void send(final Message message, Principal principal) {
        String username = principal.getName();
        log.info("Received greeting message {} from {}", message, principal.getName());
        userStore.registerUser(principal.getName());

        final String time = new SimpleDateFormat("HH:mm").format(new Date());
        OutputMessage outputMessage = new OutputMessage(message.getFrom(), "Thank you for registering", time);

        messagingTemplate.convertAndSendToUser(username, WsConfig.SUBSCRIBE_USER_REPLY, outputMessage);

        OutputMessage outputMessage2 = new OutputMessage(message.getFrom(), "Someone just registered saying " + message.getText(), time);

        messagingTemplate.convertAndSend(WsConfig.SUBSCRIBE_QUEUE, outputMessage2);
    }
}

