package org.example.chatbackend.application.dtos;

import lombok.Data;

@Data
public class SendMessageRequest {

    private Long senderId;

    private String content;

}
