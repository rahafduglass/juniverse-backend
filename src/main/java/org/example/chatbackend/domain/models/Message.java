package org.example.chatbackend.domain.models;

import jakarta.persistence.*;
import org.example.chatbackend.domain.enums.MessageStatus;
import org.example.chatbackend.persistance.entities.ChatEntity;
import org.example.chatbackend.persistance.entities.UserEntity;

import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.LocalDateTime;

public class Message {

    private int id;

    private String content;

    private LocalDateTime localDateTime;

    private int senderId;

    private int chatId; // to get type "private" or "public"

    private MessageStatus status;
}
