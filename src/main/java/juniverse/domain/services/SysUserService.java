package juniverse.domain.services;

import juniverse.domain.mappers.SysUserMapper;
import juniverse.domain.models.SysUserModel;
import juniverse.domain.provider.IdentityProvider;
import juniverse.persistance.repositories.SysUserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
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


    public Boolean updateBio(SysUserModel sysUserModel) {
        sysUserModel.setId(identityProvider.currentIdentity().getId());
        return sysUserRepository.updateBio(sysUserMapper.modelToEntity(sysUserModel));
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
        return sysUserRepository.updateProfilePicturePath(identityProvider.currentIdentity().getId(), filePath);
    }

    public String getProfilePicture() throws IOException {
        //get photo
        String photoPath = sysUserRepository.findProfilePicturePath(identityProvider.currentIdentity().getId());
        if (photoPath == null) return null;

        FileInputStream fileInputStream = new FileInputStream(photoPath);

        //encode picture then return it
        return Base64.getEncoder().encodeToString(fileInputStream.readAllBytes());
    }

    public String getCoverPicture() throws IOException {
        //get photo
        String photoPath = sysUserRepository.findCoverPicturePath(identityProvider.currentIdentity().getId());
        if (photoPath == null) return null;

        FileInputStream fileInputStream = new FileInputStream(photoPath);

        //encode picture then return it
        return Base64.getEncoder().encodeToString(fileInputStream.readAllBytes());

    }

    public boolean updateCoverPicture(String pictureAsBase64) throws IOException {
        //decode for more efficient storage
        byte[] decodedPhoto = Base64.getDecoder().decode(pictureAsBase64);

        //generate a unique path
        String username = identityProvider.currentIdentity().getUsername();
        String fileName = username + "_cover.png";
        String filePath = "src\\main\\resources\\juniverse_files\\cover_pictures\\" + fileName;

        //store it in pc local storage
        FileOutputStream fileOutputStream = new FileOutputStream(filePath);
        fileOutputStream.write(decodedPhoto);
        fileOutputStream.close();

        //if storing in local storage is successful, store path in the database
        return sysUserRepository.updateCoverPicturePath(identityProvider.currentIdentity().getId(), filePath);
    }
}
