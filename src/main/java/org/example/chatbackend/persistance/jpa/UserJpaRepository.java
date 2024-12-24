package org.example.chatbackend.persistance.jpa;

import org.example.chatbackend.persistance.entities.SysUserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserJpaRepository extends JpaRepository<SysUserEntity, Long> {
}
