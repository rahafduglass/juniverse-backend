package juniverse.domain.services;

import juniverse.domain.enums.FileExtension;
import juniverse.domain.enums.FileStatus;
import juniverse.domain.provider.IdentityProvider;
import juniverse.persistance.entities.SysUserEntity;
import juniverse.persistance.repositories.FileRepository;
import juniverse.persistance.repositories.FolderRepository;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import juniverse.domain.models.FileModel;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.Base64;

@RequiredArgsConstructor
@Service
public class FileService {
    private final FileRepository fileRepository;
    private final IdentityProvider identityProvider;
    private final FolderRepository folderRepository;

    public boolean addFolder(FileModel fileModel, String fileAsBase64) throws IOException {

        validateFile(fileModel);

        SysUserEntity currentUser = identityProvider.currentIdentity();
        //fill default data
        fileModel.setOwnerId(currentUser.getId());
        fileModel.setOwnerUsername(currentUser.getUsername());
        fileModel.setStatus(FileStatus.PENDING);
        fileModel.setUploadDate(LocalDateTime.now());
        fileModel.setPath("src\\main\\resources\\juniverse_files\\folders\\"+fileModel.getFolderId()+"\\");
        FileModel savedFile = fileRepository.addFile(fileModel);

        //TODO decode base64 and store in local storage
        byte[] decodedFile=Base64.getDecoder().decode(fileAsBase64);
        String filePath="src\\main\\resources\\juniverse_files\\folders\\"+fileModel.getFolderId()+"\\"+ savedFile.getId()+"."+savedFile.getExtension();

        FileOutputStream fileOutputStream= new FileOutputStream(filePath);
        fileOutputStream.write(decodedFile);
        fileOutputStream.close();

        return true;
    }

    private void validateFile(FileModel fileModel) {
        try {
            FileExtension.valueOf((fileModel.getExtension()).name());
        } catch (Exception e) {
            throw new RuntimeException("file extension isn't valid");
        }

        if (fileModel.getDescription().isEmpty() || fileModel.getName().isEmpty()) {
            throw new RuntimeException("file data isn't valid");
        }

        if (fileModel.getFolderId() == null || folderRepository.findById(fileModel.getFolderId()) == null) {
            throw new RuntimeException("folder id isn't valid");
        }

    }
}
