package org.example.chatbackend.application.dtos;

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
public class MessageResponse {

    private Long id;

    private String content;

    private LocalDateTime localDateTime;

    private Long senderId;

    private Long chatId;

    private MessageStatus status;

}
