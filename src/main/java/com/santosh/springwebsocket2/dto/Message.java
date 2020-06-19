package com.santosh.springwebsocket2.dto;

import lombok.ToString;

@ToString
public class Message {

    private String from;
    private String text;

    public String getText() {
        return text;
    }

    public String getFrom() {
        return from;
    }
}