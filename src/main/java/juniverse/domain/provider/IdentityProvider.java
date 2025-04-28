package juniverse.domain.provider;


import juniverse.persistance.entities.user.SysUserEntity;
import juniverse.persistance.repositories.user.SysUserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class IdentityProvider {

    private final SysUserRepository sysUserRepository;

    public SysUserEntity currentIdentity() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if(authentication != null && authentication.isAuthenticated()) {
            if(authentication.getPrincipal() instanceof UserDetails userDetails) {
                return sysUserRepository.findByUsername(userDetails.getUsername()).get();
            }

            if(authentication.getPrincipal() instanceof String username) {
                return sysUserRepository.findByUsername(username).get();
            }
        }

        return null;
    }
}
