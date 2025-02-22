package juniverse.persistance.repositories;

import juniverse.domain.models.FolderModel;
import org.springframework.stereotype.Repository;

@Repository
public interface FolderRepository {
    FolderModel findByName(String name);

    Boolean save(FolderModel folderModel);

}
