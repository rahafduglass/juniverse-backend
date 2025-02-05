package juniverse.chatbackend.domain.services;

import juniverse.chatbackend.application.dtos.RegisterRequest;
import juniverse.chatbackend.application.dtos.authentication.UserAuthenticationRequest;
import juniverse.chatbackend.application.dtos.authentication.UserAuthenticationResponse;
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



    public UserAuthenticationResponse signIn(UserAuthenticationRequest userAuthenticationRequest) {

        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(userAuthenticationRequest.getUsername(), userAuthenticationRequest.getPassword()));

        var user = sysUserRepository.findByUsername(userAuthenticationRequest.getUsername())
                .orElseThrow(() -> new RuntimeException("invalid credentials"));
        var jwt = jwtService.generateToken(user);
        var refreshToken = jwtService.generateRefreshToken(new HashMap<>(), user);


        UserAuthenticationResponse userAuthenticationResponse = new UserAuthenticationResponse();
        userAuthenticationResponse.setToken(jwt);
        //userAuthenticationResponse.setRefreshToken(refreshToken);
        return userAuthenticationResponse;
    }

    public Boolean registerListOfUsers(List<RegisterRequest> registerRequests) {
        List<SysUserEntity> users = registerRequests.stream().map(registerRequest -> {
            SysUserEntity user = new SysUserEntity();
            user.setUsername(registerRequest.getUsername());
            user.setEmail(registerRequest.getEmail());
            user.setFirstName(registerRequest.getFirstName());
            user.setLastName(registerRequest.getLastName());
            user.setRole(registerRequest.getRole());
            user.setPassword(passwordEncoder.encode(registerRequest.getPassword()));
            return user;
        }).collect(Collectors.toList());

        List<SysUserEntity> savedUsers = sysUserRepository.saveAll(users);

        return savedUsers!=null;
    }
}