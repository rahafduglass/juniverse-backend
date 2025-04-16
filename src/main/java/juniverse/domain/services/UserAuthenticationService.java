package juniverse.domain.services;

import juniverse.domain.mappers.SysUserMapper;
import juniverse.domain.models.SysUserModel;
import juniverse.domain.models.UserAuthenticationModel;
import juniverse.domain.services.security.JwtService;
import juniverse.persistance.entities.SysUserEntity;
import juniverse.persistance.repositories.SysUserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.LockedException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class UserAuthenticationService {

    private final SysUserRepository sysUserRepository;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;
    private final PasswordEncoder passwordEncoder;
    private final SysUserMapper sysUserMapper;


    public UserAuthenticationModel signIn(UserAuthenticationModel userAuthenticationModel) {

        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(userAuthenticationModel.getUsername(), userAuthenticationModel.getPassword()));

        var user = sysUserRepository.findByUsername(userAuthenticationModel.getUsername())
                .orElseThrow(() -> new RuntimeException("invalid credentials"));
        if (user.getIsBanned()) {
            throw new LockedException("This user is banned.");
        }
        var jwt = jwtService.generateToken(user);


        return new UserAuthenticationModel(null, null, jwt);

    }

    public Boolean registerListOfUsers(List<SysUserModel> sysUserModels) {

        List<SysUserEntity> users = sysUserModels.stream().map(sysUserModel -> {

            SysUserEntity user = sysUserMapper.modelToEntity(sysUserModel);
            user.setPassword(passwordEncoder.encode(sysUserModel.getPassword()));
            return user;
        }).collect(Collectors.toList());

        List<SysUserEntity> savedUsers = sysUserRepository.saveAll(users);
        return savedUsers != null;
    }

}