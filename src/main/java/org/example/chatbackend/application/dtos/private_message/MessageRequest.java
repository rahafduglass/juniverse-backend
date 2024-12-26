package org.example.chatbackend.application.dtos.private_message;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class MessageRequest {

    private Long senderId;

    private Long receiverId;

    private String content;

}
