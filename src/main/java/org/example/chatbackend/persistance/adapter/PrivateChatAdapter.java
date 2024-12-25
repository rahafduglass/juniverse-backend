package org.example.chatbackend.persistance.adapter;

import lombok.RequiredArgsConstructor;
import org.example.chatbackend.domain.mappers.PrivateChatMapper;
import org.example.chatbackend.domain.mappers.UserMapper;
import org.example.chatbackend.domain.models.PrivateChatModel;
import org.example.chatbackend.domain.models.UserModel;
import org.example.chatbackend.persistance.entities.PrivateChatEntity;
import org.example.chatbackend.persistance.entities.SysUserEntity;
import org.example.chatbackend.persistance.jpa.PrivateChatJpaRepository;
import org.example.chatbackend.persistance.repositories.PrivateChatRepository;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor

public class PrivateChatAdapter implements PrivateChatRepository {

    private final PrivateChatJpaRepository privateChatJpaRepository;
    private final UserMapper userMapper;
    private final PrivateChatMapper privateChatMapper;

    @Override
    public PrivateChatModel findPrivateChatByTherapistAndUser(UserModel sender, UserModel receiver) {
        return privateChatMapper.entityToModel(privateChatJpaRepository.findPrivateChatEntityByTherapistAndAndUser(userMapper.modelToEntity(sender), userMapper.modelToEntity(receiver)));
    }

    @Override
    public PrivateChatModel findPrivateChatByUser(UserModel sender) {
        return privateChatMapper.entityToModel(privateChatJpaRepository.findPrivateChatEntityByUser(userMapper.modelToEntity(sender)));
    }

    @Override
    public PrivateChatModel createPrivateChat(PrivateChatModel privateChat) {
        return privateChatMapper.entityToModel(privateChatJpaRepository.save(privateChatMapper.modelToEntity(privateChat)));
    }


}
