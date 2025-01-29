package juniverse.chatbackend.persistance.adapter;

import lombok.RequiredArgsConstructor;
import juniverse.chatbackend.domain.mappers.PrivateChatMapper;
import juniverse.chatbackend.domain.mappers.UserMapper;
import juniverse.chatbackend.domain.models.PrivateChatModel;
import juniverse.chatbackend.domain.models.UserModel;
import juniverse.chatbackend.persistance.jpa.PrivateChatJpaRepository;
import juniverse.chatbackend.persistance.repositories.PrivateChatRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@RequiredArgsConstructor
public class PrivateChatAdapter implements PrivateChatRepository {

    private final PrivateChatJpaRepository privateChatJpaRepository;
    private final UserMapper userMapper;
    private final PrivateChatMapper privateChatMapper;


    @Override
    public PrivateChatModel findPrivateChatByUser(UserModel sender) {
        return privateChatMapper.entityToModel(privateChatJpaRepository.findPrivateChatEntityByUser(userMapper.modelToEntity(sender)));
    }

    @Override
    public PrivateChatModel createPrivateChat(PrivateChatModel privateChat) {
        return privateChatMapper.entityToModel(privateChatJpaRepository.save(privateChatMapper.modelToEntity(privateChat)));
    }

    @Override
    public List<PrivateChatModel> findAllByTherapistId(Long therapistId) {
        return privateChatMapper.listOfEntitiesToListOfModels(privateChatJpaRepository.findAllByTherapistId(therapistId));
    }


}
