package juniverse.chatbackend.domain.services;


import lombok.RequiredArgsConstructor;
import juniverse.chatbackend.domain.models.SysUserModel;
import juniverse.chatbackend.persistance.repositories.UserRepository;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class SysUserService {

    private final UserRepository userRepository;

    public SysUserModel authenticateUser(SysUserModel sysUserModel) throws Exception  {
        SysUserModel sysUserModel1 = userRepository.findUserById(sysUserModel.getId());
        if (sysUserModel1 == null) throw new Exception("Wrong ID");//user not found
        else if (sysUserModel1.getPassword().equals(sysUserModel.getPassword())) return sysUserModel1;
        else throw new Exception("wrong Password");// wrong Password
    }


}
