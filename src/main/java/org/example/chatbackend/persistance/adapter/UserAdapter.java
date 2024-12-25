package org.example.chatbackend.persistance.adapter;

import lombok.RequiredArgsConstructor;
import org.example.chatbackend.domain.mappers.UserMapper;
import org.example.chatbackend.domain.models.UserModel;
import org.example.chatbackend.persistance.entities.SysUserEntity;
import org.example.chatbackend.persistance.jpa.UserJpaRepository;
import org.example.chatbackend.persistance.repositories.UserRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class UserAdapter implements UserRepository {

    private final UserJpaRepository userJpaRepository;
    private final UserMapper userMapper;

    @Override
    public UserModel findUserById(Long id) {
        Optional<SysUserEntity> userEntity=userJpaRepository.findById(id);

        if(userEntity.isEmpty()) {return null;}
        return userMapper.entityToModel(userEntity.get());
    }
}
