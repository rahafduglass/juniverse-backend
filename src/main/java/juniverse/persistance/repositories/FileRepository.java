package juniverse.persistance.repositories;

import juniverse.domain.models.FileModel;
import org.springframework.stereotype.Repository;

@Repository
public interface FileRepository {
    FileModel addFile(FileModel fileModel);
}
