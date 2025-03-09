package juniverse.persistance.jpa;

import juniverse.domain.models.FileModel;
import juniverse.persistance.entities.FileEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface FileJpaRepository extends JpaRepository<FileEntity, Long> {

    FileEntity addFile(FileEntity fileEntity);
}
