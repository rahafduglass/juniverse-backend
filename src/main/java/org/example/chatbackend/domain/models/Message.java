package org.example.chatbackend.domain.models;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.example.chatbackend.domain.enums.MessageStatus;

import java.time.LocalDateTime;
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Message {

    private Long id;

    private String content;

    private LocalDateTime localDateTime;

    private int senderId;

    private int chatId; //

    private MessageStatus status;
}
