package com.santosh.springwebsocket2.handler;

import com.santosh.springwebsocket2.dto.User;
import lombok.extern.slf4j.Slf4j;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.simp.SimpMessageHeaderAccessor;
import org.springframework.messaging.simp.stomp.StompCommand;
import org.springframework.messaging.simp.stomp.StompHeaderAccessor;
import org.springframework.messaging.support.ChannelInterceptor;
import org.springframework.messaging.support.MessageHeaderAccessor;

import java.util.LinkedList;
import java.util.Map;

@Slf4j
public class UserInterceptor implements ChannelInterceptor {

    @Override
    public Message<?> preSend(Message<?> message, MessageChannel channel) {
        StompHeaderAccessor accessor
                = MessageHeaderAccessor.getAccessor(message, StompHeaderAccessor.class);

        log.info("************ STOMP COMMAND *****" + accessor.getCommand());
        log.info("STOMP access destination " + accessor.getDestination());

        if (StompCommand.CONNECT.equals(accessor.getCommand())) {
            log.info("   {}", message.toString());

            Object raw = message
                    .getHeaders()
                    .get(SimpMessageHeaderAccessor.NATIVE_HEADERS);

            Object simSessionId = message
                    .getHeaders()
                    .get(SimpMessageHeaderAccessor.SESSION_ID_HEADER);

            if (raw instanceof Map) {
                Object name = ((Map) raw).get("username");

                String user = "";

                if (name instanceof LinkedList) {
                    user = ((LinkedList) name).get(0).toString();
                }

                String finalName = user + simSessionId;

                accessor.setUser(new User(finalName) {
                });
            }
        }
        return message;
    }
}
