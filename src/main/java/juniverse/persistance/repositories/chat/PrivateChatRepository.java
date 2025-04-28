package juniverse.persistance.repositories.chat;

import juniverse.domain.models.chat.PrivateChatModel;
import juniverse.persistance.entities.user.SysUserEntity;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PrivateChatRepository {


    PrivateChatModel findByUser(Long senderId);

    PrivateChatModel create(PrivateChatModel privateChat);

    List<Object[]> findAllByTherapistId(Long therapistId);

    PrivateChatModel findById(Long chatId);

    PrivateChatModel findByUser(SysUserEntity sysUserEntity);

    PrivateChatModel findByUserId(Long id);
}


