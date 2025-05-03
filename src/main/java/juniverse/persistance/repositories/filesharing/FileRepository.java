package juniverse.persistance.repositories.filesharing;

import juniverse.domain.enums.FileStatus;
import juniverse.domain.models.filesharing.FileModel;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface FileRepository {
    FileModel addFile(FileModel fileModel);

    FileModel addFileAttachmentToChat(FileModel fileModel);

    boolean updateFilePath(Long id, String filePath);

    List<FileModel> getAcceptedFiles(Long folderId);

    FileModel getFilePath(Long fileId);

    List<FileModel> getPendingFiles(Long folderId);

    List<FileModel> getPendingFiles();


    boolean updateFileStatus(Long fileId, FileStatus status, LocalDateTime monitoredAt, Long monitoredBy);

    boolean deleteFile(Long fileId);

    boolean updateFileName(Long fileId, String fileName);

    boolean updateFileDescription(Long fileId, String fileDescription);

    List<FileModel> getUserPendingFiles(Long userId);

    List<FileModel> getUserAcceptedFiles(Long userId);

    Long findUploaderIdById(Long fileId);

    FileModel getById(Long fileId);
}
