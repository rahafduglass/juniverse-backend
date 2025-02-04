package juniverse.chatbackend.persistance.adapter;

import lombok.RequiredArgsConstructor;
import juniverse.chatbackend.domain.mappers.UserMapper;
import juniverse.chatbackend.domain.models.SysUserModel;
import juniverse.chatbackend.persistance.entities.SysUserEntity;
import juniverse.chatbackend.persistance.jpa.UserJpaRepository;
import juniverse.chatbackend.persistance.repositories.UserRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class UserAdapter implements UserRepository {

    private final UserJpaRepository userJpaRepository;
    private final UserMapper userMapper;

    @Override
    public SysUserModel findUserById(Long id) {
        Optional<SysUserEntity> userEntity=userJpaRepository.findById(id);

        if(userEntity.isEmpty()) {return null;}
        return userMapper.entityToModel(userEntity.get());
    }
}
