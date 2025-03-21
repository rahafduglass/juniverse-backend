package juniverse.persistance.adapter;

import juniverse.domain.enums.FileStatus;
import juniverse.domain.mappers.FileMapper;
import juniverse.domain.models.FileModel;
import juniverse.persistance.jpa.FileJpaRepository;
import juniverse.persistance.repositories.FileRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Repository;

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
    public boolean updateFileStatus(Long fileId, FileStatus status) {
        return fileJpaRepository.updateFileStatus(fileId, status) > 0;
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
}
