package org.example.chatbackend.persistance.repositories;

import org.example.chatbackend.domain.models.PrivateChatModel;
import org.example.chatbackend.domain.models.UserModel;
import org.springframework.stereotype.Repository;

@Repository
public interface PrivateChatRepository {

    PrivateChatModel findPrivateChatByTherapistAndUser(UserModel senderId, UserModel receiverId);

    PrivateChatModel findPrivateChatByUser(UserModel senderId);

    PrivateChatModel createPrivateChat(PrivateChatModel privateChat);
}
