package juniverse.domain.services;

import juniverse.domain.enums.FileExtension;
import juniverse.domain.provider.IdentityProvider;
import juniverse.persistance.entities.SysUserEntity;
import juniverse.persistance.repositories.FileRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import juniverse.domain.models.FileModel;

import java.util.Base64;

@AllArgsConstructor
@Service
public class FileService {
    private final FileRepository fileRepository;
    private final IdentityProvider identityProvider;
    public boolean addFolder(FileModel fileModel, Base64 fileAsBase64) {

        validateFile(fileModel);

        SysUserEntity currentUser=identityProvider.currentIdentity();
        //fill default data
        fileModel.setOwnerId(currentUser.getId());
        fileModel.setOwnerUsername(currentUser.getUsername());
        fileModel.setPath("juniverse_files/folders/"+fileModel.getFolderEntityId());

        FileModel savedFile= fileRepository.addFile(fileModel);




        //TODO decode base64 and store in local storage

        return true;
    }
    private void validateFile(FileModel fileModel) {
        try{
            FileExtension extension=FileExtension.valueOf((fileModel.getExtension()).name());
        }
        catch(Exception e){
            throw new RuntimeException("file extension isn't valid");
        }

        if(fileModel.getDescription().isEmpty()||fileModel.getName().isEmpty()){
            throw new RuntimeException("file data isn't valid");
        }

        if(fileModel.getFolderEntityId()==null ){
            throw new RuntimeException("folder id isn't valid");
        }

    }
}
