package juniverse.persistance.repositories;

import juniverse.domain.enums.FileStatus;
import juniverse.domain.models.FileModel;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface FileRepository {
    FileModel addFile(FileModel fileModel);

    boolean updateFilePath(Long id, String filePath);

    List<FileModel> getAcceptedFiles(Long folderId);

    FileModel getFilePath(Long fileId);

    List<FileModel> getPendingFiles(Long folderId);

    List<FileModel> getPendingFiles();


    boolean updateFileStatus(Long fileId, FileStatus status);

    boolean deleteFile(Long fileId);

    boolean updateFileName(Long fileId, String fileName);

    boolean updateFileDescription(Long fileId, String fileDescription);

    List<FileModel> getUserPendingFiles(Long userId);

    List<FileModel> getUserAcceptedFiles(Long userId);
}
