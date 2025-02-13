package juniverse.persistance.repositories;

import juniverse.domain.models.PrivateChatModel;
import juniverse.domain.models.SysUserModel;
import juniverse.persistance.entities.PrivateChatEntity;
import juniverse.persistance.entities.SysUserEntity;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PrivateChatRepository {


    PrivateChatModel findByUser(SysUserModel senderId);

    PrivateChatModel create(PrivateChatModel privateChat);

    List<Object[]> findAllByTherapistId(Long therapistId);

    PrivateChatEntity findById(Long chatId);

    //DELETE THIS
    PrivateChatEntity findByUser(SysUserEntity sysUserEntity);

    PrivateChatEntity findByUserId(Long id);
}


