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
    public PrivateChatModel findByUser(SysUserModel sender) {
        try{
        return privateChatMapper.entityToModel(privateChatJpaRepository.findPrivateChatEntityByUser(sysUserMapper.modelToEntity(sender)));
        }catch(Exception e){
            return null;
        }
    }

    @Override
    public PrivateChatModel create(PrivateChatModel privateChat) {
        return privateChatMapper.entityToModel(privateChatJpaRepository.save(privateChatMapper.modelToEntity(privateChat)));
    }

    @Override
    public List<Object[]> findAllByTherapistId(Long therapistId) {
        return privateChatJpaRepository.findChatsWithUserDetailsByTherapist(therapistId);
    }

    @Override
    public PrivateChatEntity findById(Long chatId) {
        return privateChatJpaRepository.findById(chatId).get();
    }

    @Override
    public PrivateChatEntity findByUser(SysUserEntity sysUserEntity) {
        return privateChatJpaRepository.findByUser(sysUserEntity);
    }

    @Override
    public PrivateChatEntity findByUserId(Long userId) {
        return privateChatJpaRepository.findByUserId(userId);
    }


}
