package com.santosh.springwebsocket2.handler;

import com.santosh.springwebsocket2.service.UserStore;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.messaging.SessionConnectEvent;
import org.springframework.web.socket.messaging.SessionDisconnectEvent;
import org.springframework.web.socket.messaging.SessionSubscribeEvent;
import org.springframework.web.socket.messaging.SessionUnsubscribeEvent;

@Slf4j
@Component
public class WsEventListener {

    @Autowired
    private UserStore userStore;

    @EventListener
    public void handleSubscribeEvent(SessionSubscribeEvent event) {
        log.info("<==> handleSubscribeEvent: username=" + event.getUser().getName() + ", event=" + event);
    }

    @EventListener
    public void handleConnectEvent(SessionConnectEvent event) {
        try {
            log.info("===> handleConnectEvent: username=" + event.getUser().getName() + ", event=" + event);
            userStore.registerUser(event.getUser().getName());
        } catch (Exception e) {
            log.error("Exception ", e);
        }
    }

    @EventListener
    public void handleDisconnectEvent(SessionDisconnectEvent event) {
        try {
            log.info("<=== handleDisconnectEvent: username=" + event.getUser().getName() + ", event=" + event);
            userStore.unregisterUser(event.getUser().getName());
        } catch (Exception e) {
            log.error("Exception ", e);
        }
    }


    @EventListener
    public void handleUnSubscribeEvent(SessionUnsubscribeEvent event) {
        log.info("<==> handleUnSubscribeEvent: username=" + event.getUser().getName() + ", event=" + event);
    }
}
