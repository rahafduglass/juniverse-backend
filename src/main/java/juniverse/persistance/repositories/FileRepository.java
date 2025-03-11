package juniverse.persistance.repositories;

import juniverse.domain.models.FileModel;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface FileRepository {
    FileModel addFile(FileModel fileModel);

    boolean updateFilePath(Long id, String filePath);

    List<FileModel> getAcceptedFiles(Long folderId);
}
