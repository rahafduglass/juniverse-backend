package juniverse.chatbackend.domain.services.security;


import juniverse.chatbackend.persistance.repositories.SysUserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.security.core.userdetails.UserDetailsService;

@Service
@RequiredArgsConstructor
public class SysUserDetailsService {

    private final SysUserRepository sysUserRepository;


    public UserDetailsService userDetailsService(){
        return new UserDetailsService() {
            @Override
            public UserDetails loadUserByUsername(String username){
                return sysUserRepository.findByUsername(username)
                        .orElseThrow(() -> new UsernameNotFoundException(username));
            }
        };
    }
}
