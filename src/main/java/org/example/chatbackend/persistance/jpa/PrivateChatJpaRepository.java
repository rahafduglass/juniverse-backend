package org.example.chatbackend.persistance.jpa;

import org.example.chatbackend.domain.models.PrivateChatModel;
import org.example.chatbackend.domain.models.UserModel;
import org.example.chatbackend.persistance.entities.PrivateChatEntity;
import org.example.chatbackend.persistance.entities.SysUserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PrivateChatJpaRepository extends JpaRepository<PrivateChatEntity,Long> {

    PrivateChatEntity findPrivateChatEntityByTherapistAndAndUser(SysUserEntity senderId, SysUserEntity receiverId);

    PrivateChatEntity findPrivateChatEntityByUser(SysUserEntity senderId);
}
