package com.santosh.springwebsocket2.service;

import com.santosh.springwebsocket2.config.WsConfig;
import com.santosh.springwebsocket2.dto.OutputMessage;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Component;

import java.text.SimpleDateFormat;
import java.util.Date;

@Slf4j
@Component
public class MessageSender {
    @Autowired
    private SimpMessagingTemplate messagingTemplate;
    @Autowired
    private UserStore userStore;

    public void sendMessageToAllUsers() {
        for (String user : userStore.getUsers()) {
            log.info("Sending msg to : " + user);

            final String time = new SimpleDateFormat("HH:mm").format(new Date());
            OutputMessage outputMessage = new OutputMessage(user, "Hallo " + user + " at", time);

            messagingTemplate.convertAndSend(WsConfig.SUBSCRIBE_QUEUE,
                    outputMessage);
        }
    }

    public void sendMessage(String user, String message) {
        log.info("Sending msg to : " + user);

        final String time = new SimpleDateFormat("HH:mm").format(new Date());
        OutputMessage outputMessage = new OutputMessage(user, message, time);

        messagingTemplate.convertAndSendToUser(user, WsConfig.SUBSCRIBE_USER_REPLY, outputMessage);
    }
}
