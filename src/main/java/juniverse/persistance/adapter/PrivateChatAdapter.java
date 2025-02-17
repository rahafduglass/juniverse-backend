package juniverse.persistance.adapter;

import juniverse.domain.mappers.SysUserMapper;
import juniverse.persistance.entities.PrivateChatEntity;
import juniverse.persistance.entities.SysUserEntity;
import lombok.RequiredArgsConstructor;
import juniverse.domain.mappers.PrivateChatMapper;
import juniverse.domain.models.PrivateChatModel;
import juniverse.domain.models.SysUserModel;
import juniverse.persistance.jpa.PrivateChatJpaRepository;
import juniverse.persistance.repositories.PrivateChatRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@RequiredArgsConstructor
public class PrivateChatAdapter implements PrivateChatRepository {

    private final PrivateChatJpaRepository privateChatJpaRepository;
    private final SysUserMapper sysUserMapper;
    private final PrivateChatMapper privateChatMapper;


    @Override
    public PrivateChatModel findByUser(Long userId) {
        try{
        return privateChatMapper.entityToModel(privateChatJpaRepository.findPrivateChatEntityByUser(userId));
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
    public PrivateChatModel findById(Long chatId) {
        return privateChatMapper.entityToModel(privateChatJpaRepository.findById(chatId).get());
    }

    @Override
    public PrivateChatModel findByUser(SysUserEntity sysUserEntity) {
        return privateChatMapper.entityToModel(privateChatJpaRepository.findByUser(sysUserEntity));
    }

    @Override
    public PrivateChatModel findByUserId(Long userId) {
        return privateChatMapper.entityToModel(privateChatJpaRepository.findByUserId(userId));
    }


}
