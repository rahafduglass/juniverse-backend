package org.example.chatbackend.domain.services;


import lombok.RequiredArgsConstructor;
import org.example.chatbackend.domain.mappers.UserMapper;
import org.example.chatbackend.domain.models.UserModel;
import org.example.chatbackend.persistance.entities.SysUserEntity;
import org.example.chatbackend.persistance.repositories.UserRepository;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final UserMapper userMapper;

    public UserModel authenticateUser(UserModel userModel) {
        Optional<SysUserEntity> optionalUserEntity = userRepository.findUserById(userModel.getId());
        if (optionalUserEntity.isEmpty())
            return null; // user doesnt exist
        UserModel userModel1 = userMapper.entityToModel(optionalUserEntity.get());
        if (!userModel1.getPassword().equals(userModel.getPassword()))
            return userModel1;
        else return null; //wrong password

    }

}
