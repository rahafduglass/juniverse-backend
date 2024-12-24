package org.example.chatbackend.persistance.adapter;

import lombok.RequiredArgsConstructor;
import org.example.chatbackend.persistance.entities.SysUserEntity;
import org.example.chatbackend.persistance.jpa.UserJpaRepository;
import org.example.chatbackend.persistance.repositories.UserRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class UserAdapter implements UserRepository {

    private final UserJpaRepository userJpaRepository;

    @Override
    public Optional<SysUserEntity> findUserById(Long id) {
        return userJpaRepository.findById(id);
    }
}
