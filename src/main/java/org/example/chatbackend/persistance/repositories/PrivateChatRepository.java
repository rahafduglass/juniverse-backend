package org.example.chatbackend.persistance.repositories;

import org.example.chatbackend.domain.models.PrivateChatModel;
import org.example.chatbackend.domain.models.UserModel;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PrivateChatRepository {


    PrivateChatModel findPrivateChatByUser(UserModel senderId);

    PrivateChatModel createPrivateChat(PrivateChatModel privateChat);

    List<PrivateChatModel> findAllByTherapistId(Long therapistId);
}
