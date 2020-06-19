package com.santosh.springwebsocket2.controller;

import com.santosh.springwebsocket2.dto.BaseResponse;
import com.santosh.springwebsocket2.dto.SendMessageRequest;
import com.santosh.springwebsocket2.dto.UserListResponse;
import com.santosh.springwebsocket2.service.MessageSender;
import com.santosh.springwebsocket2.service.UserStore;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@Slf4j
@RestController
@RequestMapping("user/session")
public class SessionController {
    @Autowired
    private UserStore userStore;
    @Autowired
    private MessageSender messageSender;

    @GetMapping(value = "connected", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> getConnectedUserList() {
        List<String> users = userStore.getUsers();

        List<UserListResponse> userListResponse = new ArrayList<>();

        for (String user : users) {
            UserListResponse userObj = new UserListResponse();
            userObj.setUser(user);

            userListResponse.add(userObj);
        }
        return new ResponseEntity<>(userListResponse, HttpStatus.OK);
    }

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> sendMessageToUser(@RequestBody SendMessageRequest request) {

        messageSender.sendMessage(request.getRecipient(), request.getMessage());

        BaseResponse baseResponse = new BaseResponse();
        baseResponse.setSuccess(true);
        baseResponse.setMessage("Message Sent");

        return new ResponseEntity<>(baseResponse, HttpStatus.OK);
    }
}