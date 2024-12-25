package org.example.chatbackend.persistance.repositories;

import org.example.chatbackend.domain.models.UserModel;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository {
    UserModel findUserById(Long id);
}
