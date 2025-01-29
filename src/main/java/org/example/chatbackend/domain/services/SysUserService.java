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
public class SysUserService {

    private final UserRepository userRepository;

    public UserModel authenticateUser(UserModel userModel) throws Exception  {
        UserModel userModel1 = userRepository.findUserById(userModel.getId());
        if (userModel1 == null) throw new Exception("Wrong ID");//user not found
        else if (userModel1.getPassword().equals(userModel.getPassword())) return userModel1;
        else throw new Exception("wrong Password");// wrong Password
    }


}
