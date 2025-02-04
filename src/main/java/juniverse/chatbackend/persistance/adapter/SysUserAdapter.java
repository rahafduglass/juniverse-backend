package juniverse.chatbackend.persistance.adapter;

import lombok.RequiredArgsConstructor;
import juniverse.chatbackend.domain.mappers.UserMapper;
import juniverse.chatbackend.domain.models.SysUserModel;
import juniverse.chatbackend.persistance.entities.SysUserEntity;
import juniverse.chatbackend.persistance.jpa.SysUserJpaRepository;
import juniverse.chatbackend.persistance.repositories.SysUserRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class SysUserAdapter implements SysUserRepository {

    private final SysUserJpaRepository sysUserJpaRepository;
    private final UserMapper userMapper;

    @Override
    public SysUserModel findUserById(Long id) {
        Optional<SysUserEntity> userEntity= sysUserJpaRepository.findById(id);

        if(userEntity.isEmpty()) {return null;}
        return userMapper.entityToModel(userEntity.get());
    }

    @Override
    public Optional<SysUserEntity> findByUsername(String username) {
        return sysUserJpaRepository.findByUsername(username);
    }

    @Override
    public List<SysUserEntity> saveAll(List<SysUserEntity> users) {
        return sysUserJpaRepository.saveAll(users);
    }
}
