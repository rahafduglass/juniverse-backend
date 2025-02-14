package juniverse.domain.services;

import juniverse.domain.mappers.SysUserMapper;
import juniverse.domain.models.SysUserModel;
import juniverse.domain.provider.IdentityProvider;
import juniverse.persistance.entities.SysUserEntity;
import juniverse.persistance.repositories.SysUserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Base64;

@Service
@RequiredArgsConstructor
public class SysUserService {

    private final IdentityProvider identityProvider;
    private final SysUserRepository sysUserRepository;
    private final SysUserMapper sysUserMapper;

    // region getProfile
    public SysUserModel getProfile() {
        return sysUserMapper.entityToModel(sysUserRepository.findByUsername(identityProvider.currentIdentity().getUsername()).get());
    }
    // endregion

    // region updateBio
    public Boolean updateBio(SysUserModel sysUserModel) {
        sysUserModel.setId(identityProvider.currentIdentity().getId());
        return sysUserRepository.updateBio(sysUserMapper.modelToEntity(sysUserModel));
    }
    // endregion

    // region updateProfilePicture
    public boolean updateProfilePicture(String pictureAsBase64,String fileExtension ) throws IOException {
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
        return sysUserRepository.updateProfilePicturePath(identityProvider.currentIdentity().getId(), filePath,fileExtension);
    }
    // endregion

    // region getProfilePicture
    public Object [] getProfilePicture() throws IOException {
        //get photo
        Object [] photo = sysUserRepository.findProfilePicturePath(identityProvider.currentIdentity().getId());
        //if null return default cover pic
        if (((Object[])photo[0])[0] == null) {
            ((Object[])photo[0])[0]= Files.readString(Paths.get("src/main/resources/juniverse_files/defaults/default_profile_pic.txt"));
            ((Object[])photo[0])[1]="jpeg";
            return photo;
        }

        //encode picture then return it
        FileInputStream fileInputStream = new FileInputStream((String) ((Object[])photo[0])[0]);
        ((Object[])photo[0])[0]=Base64.getEncoder().encodeToString(fileInputStream.readAllBytes());

        return photo;
    }
    // endregion

    // region getCoverPicture
    public Object[] getCoverPicture() throws IOException {
        //get photo
        Object[] photo = sysUserRepository.findCoverPicturePath(identityProvider.currentIdentity().getId());
        //if null return default cover pic
        if (((Object[])photo[0])[0] == null) {
            ((Object[])photo[0])[0]=Files.readString(Paths.get("src/main/resources/juniverse_files/defaults/default_cover_pic.txt"));;
            ((Object[])photo[0])[1]="png";
            return photo;
        }

        //encode picture then return it
        FileInputStream fileInputStream = new FileInputStream((String)((Object[])photo[0])[0]);
        ((Object[])photo[0])[0]=Base64.getEncoder().encodeToString(fileInputStream.readAllBytes());

        return photo;

    }
    // endregion

    // region updateCoverPicture
    public boolean updateCoverPicture(String pictureAsBase64,String fileExtension) throws IOException {
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
        return sysUserRepository.updateCoverPicturePath(identityProvider.currentIdentity().getId(), filePath,fileExtension);
    }

    public boolean deleteProfilePicture() throws Exception {
        Long currentUserId=identityProvider.currentIdentity().getId();

        //make sure profile pic exists
        Object [] photo = sysUserRepository.findProfilePicturePath(currentUserId);
        if(((Object[])photo[0])[0] == null)
            throw new Exception("there's no profile picture for this user");

        //delete path from database
        boolean result=sysUserRepository.deleteProfilePicture(currentUserId);

        //delete from local storage
        Files.delete(Paths.get(((Object[])photo[0])[0].toString()));

        return result ;
    }
    // endregion
}
