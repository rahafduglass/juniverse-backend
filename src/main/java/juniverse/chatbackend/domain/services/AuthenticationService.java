package juniverse.chatbackend.domain.services;

import juniverse.chatbackend.application.dtos.RegisterRequest;
import juniverse.chatbackend.application.dtos.authentication.UserAuthenticationRequest;
import juniverse.chatbackend.application.dtos.authentication.UserAuthenticationResponse;
import juniverse.chatbackend.domain.mappers.SysUserMapper;
import juniverse.chatbackend.domain.models.SysUserModel;
import juniverse.chatbackend.domain.models.UserAuthenticationModel;
import juniverse.chatbackend.domain.services.security.JwtService;
import juniverse.chatbackend.persistance.entities.SysUserEntity;
import juniverse.chatbackend.persistance.repositories.SysUserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class AuthenticationService {

    private final SysUserRepository sysUserRepository;

    private final JwtService jwtService;

    private final AuthenticationManager authenticationManager;

    private final PasswordEncoder passwordEncoder;
    private final SysUserMapper sysUserMapper;


    public UserAuthenticationModel signIn(UserAuthenticationModel userAuthenticationModel) {

        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(userAuthenticationModel.getUsername(), userAuthenticationModel.getPassword()));

        //authenticate user
        var user = sysUserRepository.findByUsername(userAuthenticationModel.getUsername())
                .orElseThrow(() -> new RuntimeException("invalid credentials"));
        //generate token
        var jwt = jwtService.generateToken(user);

        //return model with token only
        return new UserAuthenticationModel(null, null, jwt);

    }

    public Boolean registerListOfUsers(List<SysUserModel> sysUserModels) {

        List<SysUserEntity> users = sysUserModels.stream().map(sysUserModel -> {
            //map model to entity
            SysUserEntity user= sysUserMapper.modelToEntity(sysUserModel);
            //encode the password THEN map it.
            user.setPassword(passwordEncoder.encode(sysUserModel.getPassword()));
            return user;
        }).collect(Collectors.toList());

        List<SysUserEntity> savedUsers = sysUserRepository.saveAll(users);
        return savedUsers!=null;
    }

}