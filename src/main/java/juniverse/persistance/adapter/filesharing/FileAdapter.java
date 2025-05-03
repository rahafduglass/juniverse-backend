package juniverse.persistance.adapter.filesharing;

import juniverse.domain.enums.FileStatus;
import juniverse.domain.mappers.filesharing.FileMapper;
import juniverse.domain.models.filesharing.FileModel;
import juniverse.persistance.entities.filesharing.FileEntity;
import juniverse.persistance.jpa.filesharing.FileJpaRepository;
import juniverse.persistance.repositories.filesharing.FileRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@AllArgsConstructor
@Repository
public class FileAdapter implements FileRepository {


    private final FileJpaRepository fileJpaRepository;
    private final FileMapper fileMapper;

    @Override
    public FileModel addFile(FileModel fileModel) {
        return fileMapper.entityToModel(fileJpaRepository.save(fileMapper.modelToEntity(fileModel)));
    }

    @Override
    public FileModel addFileAttachmentToChat(FileModel fileModel) {
        FileEntity file= fileMapper.modelToEntity(fileModel);
        file.setFolder(null);
        return fileMapper.entityToModel(fileJpaRepository.save(file));
    }

    @Override
    public boolean updateFilePath(Long id, String filePath) {
        return fileJpaRepository.updatePath(id, filePath) > 0;
    }

    @Override
    public List<FileModel> getAcceptedFiles(Long folderId) {
        return (fileJpaRepository.findAllByStatus(folderId, FileStatus.ACCEPTED)).stream().map(fileMapper::entityToModel).toList();
    }

    @Override
    public FileModel getFilePath(Long fileId) {
        return fileMapper.entityToModel(fileJpaRepository.findById(fileId).get());
    }

    @Override
    public List<FileModel> getPendingFiles(Long folderId) {

        return fileJpaRepository.findAllByStatus(folderId, FileStatus.PENDING).stream()
                .map(e ->
                        {
                            FileModel fileModel = fileMapper.entityToModel(e);
                            fileModel.setFolderName(e.getFolder().getName());
                            return fileModel;
                        }
                ).toList();
    }

    @Override
    public List<FileModel> getPendingFiles() {
        return fileJpaRepository.findAllByStatus(FileStatus.PENDING).stream()
                .map(e ->
                {
                    FileModel fileModel = fileMapper.entityToModel(e);
                    fileModel.setFolderName(e.getFolder().getName());
                    return fileModel;
                })
                .toList();
    }

    @Override
    public boolean updateFileStatus(Long fileId, FileStatus status, LocalDateTime monitoredAt, Long monitoredBy) {
        return fileJpaRepository.updateFileStatus(fileId, status,monitoredAt,monitoredBy) > 0;
    }

    @Override
    public boolean deleteFile(Long fileId) {
        fileJpaRepository.delete(fileJpaRepository.findById(fileId).get());
        return true;
    }

    @Override
    public boolean updateFileName(Long fileId, String fileName) {
        return fileJpaRepository.updateFileName(fileId,fileName)>0;

    }

    @Override
    public boolean updateFileDescription(Long fileId, String fileDescription) {
        return fileJpaRepository.updateFileDescription(fileId,fileDescription)>0;
    }

    @Override
    public List<FileModel> getUserPendingFiles(Long userId) {
        return (fileJpaRepository.getUserPendingFiles(userId)).stream().map(fileMapper::entityToModel).toList();
    }

    @Override
    public List<FileModel> getUserAcceptedFiles(Long userId) {
        return (fileJpaRepository.getUserAcceptedFiles(userId)).stream().map(fileMapper::entityToModel).toList();
    }

    @Override
    public Long findUploaderIdById(Long fileId) {
        return fileJpaRepository.findOwnerIdById(fileId);
    }

    @Override
    public FileModel getById(Long fileId) {
        return fileMapper.entityToModel(fileJpaRepository.findById(fileId).get());
    }
}
