package org.example.chatbackend.persistance.jpa;

import org.example.chatbackend.persistance.entities.PrivateChatEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PrivateChatJpaRepository extends JpaRepository<PrivateChatEntity,Long> {
}
