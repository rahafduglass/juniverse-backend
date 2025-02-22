package juniverse.persistance.repositories;

import juniverse.domain.models.FolderModel;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface FolderRepository {
    FolderModel findByName(String name);

    FolderModel save(FolderModel folderModel);

    FolderModel findById(Long id);

    void updatePath(Long id, String path);
}
