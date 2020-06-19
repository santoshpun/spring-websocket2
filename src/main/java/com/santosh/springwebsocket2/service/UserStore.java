package com.santosh.springwebsocket2.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Slf4j
@Component
public class UserStore {
    private List<String> users = new ArrayList<>();

    public void registerUser(String user) {
        log.info(user + " is registered for websocket notificaiton");
        if (!users.contains(user)) {
            users.add(user);
        }
    }

    public List<String> getUsers() {
        return users;
    }

    public void unregisterUser(String user) {
        List<String> updatedUserList = new ArrayList<>();
        for (String username : users) {
            if (!user.equalsIgnoreCase(username)) {
                updatedUserList.add(username);
            } else {
                log.info(username + " is unregistered for websocket notificaiton");
            }
        }
        users = updatedUserList;
    }
}
