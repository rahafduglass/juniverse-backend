package juniverse.chatbackend.persistance.adapter;

import juniverse.chatbackend.domain.mappers.SysUserMapper;
import juniverse.chatbackend.persistance.entities.PrivateChatEntity;
import juniverse.chatbackend.persistance.entities.SysUserEntity;
import lombok.RequiredArgsConstructor;
import juniverse.chatbackend.domain.mappers.PrivateChatMapper;
import juniverse.chatbackend.domain.models.PrivateChatModel;
import juniverse.chatbackend.domain.models.SysUserModel;
import juniverse.chatbackend.persistance.jpa.PrivateChatJpaRepository;
import juniverse.chatbackend.persistance.repositories.PrivateChatRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@RequiredArgsConstructor
public class PrivateChatAdapter implements PrivateChatRepository {

    private final PrivateChatJpaRepository privateChatJpaRepository;
    private final SysUserMapper sysUserMapper;
    private final PrivateChatMapper privateChatMapper;


    @Override
    public PrivateChatModel findPrivateChatByUser(SysUserModel sender) {
        return privateChatMapper.entityToModel(privateChatJpaRepository.findPrivateChatEntityByUser(sysUserMapper.modelToEntity(sender)));
    }

    @Override
    public PrivateChatModel createPrivateChat(PrivateChatModel privateChat) {
        return privateChatMapper.entityToModel(privateChatJpaRepository.save(privateChatMapper.modelToEntity(privateChat)));
    }

    @Override
    public List<Object[]> findAllByTherapistId(Long therapistId) {
        return privateChatJpaRepository.findChatsWithUserDetails(therapistId);
    }

    @Override
    public PrivateChatEntity findPrivateChatById(Long chatId) {
        return privateChatJpaRepository.findPrivateChatEntityById(chatId);
    }

    @Override
    public PrivateChatModel findByUser(SysUserEntity sysUserEntity) {
        return privateChatMapper.entityToModel(privateChatJpaRepository.findByUser(sysUserEntity));
    }


}
