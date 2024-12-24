package org.example.chatbackend.persistance.repositories;

import org.example.chatbackend.persistance.entities.SysUserEntity;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository {
    Optional<SysUserEntity> findUserById(Long id);
}
