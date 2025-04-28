package juniverse.persistance.repositories.filesharing;

import juniverse.domain.models.filesharing.FolderModel;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface FolderRepository {
    FolderModel findByName(String name);

    FolderModel save(FolderModel folderModel);

    FolderModel findById(Long id);

    void updatePath(Long id, String path);

    List<FolderModel> getFolders();

    void remove(Long folderId);
}
