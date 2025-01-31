package juniverse.chatbackend.persistance.repositories;

import juniverse.chatbackend.domain.models.PrivateChatModel;
import juniverse.chatbackend.domain.models.UserModel;
import juniverse.chatbackend.persistance.entities.PrivateChatEntity;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PrivateChatRepository {


    PrivateChatModel findPrivateChatByUser(UserModel senderId);

    PrivateChatModel createPrivateChat(PrivateChatModel privateChat);

    List<Object[]> findAllByTherapistId(Long therapistId);

    PrivateChatEntity findPrivateChatById(Long chatId);
}
