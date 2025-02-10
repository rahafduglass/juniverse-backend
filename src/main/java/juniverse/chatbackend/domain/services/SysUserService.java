package juniverse.chatbackend.domain.services;

import juniverse.chatbackend.domain.mappers.SysUserMapper;
import juniverse.chatbackend.domain.models.SysUserModel;
import juniverse.chatbackend.domain.provider.IdentityProvider;
import juniverse.chatbackend.persistance.entities.SysUserEntity;
import juniverse.chatbackend.persistance.repositories.SysUserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PutMapping;

import javax.imageio.stream.ImageInputStream;
import java.io.*;
import java.util.Base64;

@Service
@RequiredArgsConstructor
public class SysUserService {

    private final IdentityProvider identityProvider;
    private final SysUserRepository sysUserRepository;
    private final SysUserMapper sysUserMapper;

    public SysUserModel getProfile() {
        return sysUserMapper.entityToModel(sysUserRepository.findByUsername(identityProvider.currentIdentity().getUsername()).get());
    }


    public Boolean updateProfile(SysUserModel sysUserModel) {
        sysUserModel.setId(identityProvider.currentIdentity().getId());
        return sysUserRepository.updateProfile(sysUserMapper.modelToEntity(sysUserModel));
    }

    public boolean updateProfilePicture(String pictureAsBase64) throws IOException {
        //decode for more efficient storage
        byte[] decodedPhoto = Base64.getDecoder().decode(pictureAsBase64);


        //generate a unique path
        String username = identityProvider.currentIdentity().getUsername();
        String fileName = username + "_profile.png";
        String filePath = "src\\main\\resources\\juniverse_files\\profile_pictures\\" + fileName;

        //store it in pc local storage
        FileOutputStream fileOutputStream = new FileOutputStream(filePath);
        fileOutputStream.write(decodedPhoto);
        fileOutputStream.close();

        //if storing in local storage is successful, store path in the database
        sysUserRepository.updateProfilePicturePath(identityProvider.currentIdentity().getId(), filePath);
        return false;
    }

    public String getProfilePicture() throws IOException {
        //get photo
        String photoPath = sysUserRepository.findProfilePicturePath(identityProvider.currentIdentity().getId());
        FileInputStream fileInputStream = new FileInputStream(photoPath);

        //encode picture then return it
        return Base64.getEncoder().encodeToString(fileInputStream.readAllBytes());
    }
}
