package juniverse.domain.services;

import juniverse.domain.enums.UserRole;
import juniverse.domain.provider.IdentityProvider;
import juniverse.persistance.repositories.SysUserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UsersService {

    private final SysUserRepository sysUserRepository;
    private final IdentityProvider identityProvider;

    public boolean promoteStudent(Long studentId) {
        if (sysUserRepository.findRoleById(studentId) != UserRole.STUDENT)
            throw new RuntimeException("the user should be a STUDENT to promote them");
        return sysUserRepository.updateRole(studentId, UserRole.MODERATOR);
    }

    public boolean demoteModerator(Long moderatorId) {
        if (sysUserRepository.findRoleById(moderatorId) != UserRole.MODERATOR)
            throw new RuntimeException("the user should be a MODERATOR to demote them");
        return sysUserRepository.updateRole(moderatorId, UserRole.STUDENT);

    }

    public boolean banUser(Long userId) {
        UserRole userRole = sysUserRepository.findRoleById(userId);
        if (userRole == UserRole.STUDENT || userRole == UserRole.MODERATOR)
            return sysUserRepository.banUser(userId);
        throw new RuntimeException("can't Ban an admin Or therapist");

    }
}
