package juniverse.domain.services;

import io.micrometer.common.KeyValues;
import juniverse.domain.enums.FileExtension;
import juniverse.domain.enums.FileStatus;
import juniverse.domain.enums.UserRole;
import juniverse.domain.models.EncodedFileModel;
import juniverse.domain.models.FileModel;
import juniverse.domain.provider.IdentityProvider;
import juniverse.persistance.entities.SysUserEntity;
import juniverse.persistance.repositories.FileRepository;
import juniverse.persistance.repositories.FolderRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.Base64;
import java.util.List;

@RequiredArgsConstructor
@Service
public class FileService {
    private final FileRepository fileRepository;
    private final IdentityProvider identityProvider;
    private final FolderRepository folderRepository;

    public boolean addFile(FileModel fileModel, String fileAsBase64) throws IOException {

        validateFile(fileModel);

        SysUserEntity currentUser = identityProvider.currentIdentity();

        fileModel.setStatus(currentUser.getRole() == UserRole.STUDENT ? FileStatus.PENDING : FileStatus.ACCEPTED);
        fileModel.setOwnerId(currentUser.getId());
        fileModel.setOwnerUsername(currentUser.getUsername());
        fileModel.setUploadDate(LocalDateTime.now());
        fileModel.setPath("src\\main\\resources\\juniverse_files\\folders\\" + fileModel.getFolderId() + "\\");
        FileModel savedFile = fileRepository.addFile(fileModel);

        byte[] decodedFile = Base64.getDecoder().decode(fileAsBase64);
        String filePath = "src\\main\\resources\\juniverse_files\\folders\\" + fileModel.getFolderId() + "\\" + savedFile.getId() + "." + savedFile.getExtension();

        FileOutputStream fileOutputStream = new FileOutputStream(filePath);
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

        validateFolder(fileModel.getFolderId());

    }

    public List<FileModel> getAcceptedFiles(Long folderId) {

        validateFolder(folderId);
        return fileRepository.getAcceptedFiles(folderId);
    }

    private void validateFolder(Long folderId) {
        if (folderId == null || folderRepository.findById(folderId) == null) {
            throw new RuntimeException("folder id isn't valid");
        }
    }

    public EncodedFileModel getFileAsBase64(Long fileId) throws IOException {

        FileModel file = fileRepository.getFilePath(fileId);
        if (file == null)
            throw new RuntimeException("file doesn't exist");

        FileInputStream fileInputStream = new FileInputStream(file.getPath().concat(fileId.toString() + "." + file.getExtension()));

        String encodedFile = Base64.getEncoder().encodeToString(fileInputStream.readAllBytes());

        return EncodedFileModel.builder()
                .fileAsBase64(encodedFile)
                .extension(file.getExtension())
                .build();
    }

    public List<FileModel> getPendingFiles(Long folderId) {
        validateFolder(folderId);
        return fileRepository.getPendingFiles(folderId);
    }

    public List<FileModel> getPendingFiles() {
        return fileRepository.getPendingFiles();
    }

    public boolean updateFileStatus(Long fileId, FileStatus status) {
        return fileRepository.updateFileStatus(fileId, status,LocalDateTime.now(),identityProvider.currentIdentity().getId());
    }

    public boolean deleteFile(Long fileId) {
        FileModel file = fileRepository.getFilePath(fileId);
        String filePath = file.getPath().concat(fileId.toString() + "." + file.getExtension());
        File fileToDelete = new File(filePath);

        fileRepository.deleteFile(fileId);
        fileToDelete.delete();
        return true;
    }

    public boolean updateFileName(Long fileId, String fileName) {
        return fileRepository.updateFileName(fileId,fileName);
    }

    public boolean updateFileDescription(Long fileId, String fileDescription) {
        return fileRepository.updateFileDescription(fileId,fileDescription);
    }

    public List<FileModel> getUserPendingFiles() {
        Long userId=identityProvider.currentIdentity().getId();
        return fileRepository.getUserPendingFiles(userId);
    }

    public List<FileModel> getUserAcceptedFiles() {
        Long userId=identityProvider.currentIdentity().getId();
        return fileRepository.getUserAcceptedFiles(userId);
    }
}
