package juniverse.persistance.jpa;

import jakarta.transaction.Transactional;
import juniverse.domain.models.FileModel;
import juniverse.persistance.entities.FileEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface FileJpaRepository extends JpaRepository<FileEntity, Long> {

    @Transactional
    @Modifying
    @Query("UPDATE file f SET f.path= :filePath WHERE f.id= :id")
    Integer updatePath(Long id, String filePath);
}
