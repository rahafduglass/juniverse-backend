package juniverse.persistance.jpa;

import jakarta.transaction.Transactional;
import juniverse.persistance.entities.FolderEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface FolderJpaRepository extends JpaRepository<FolderEntity, Long> {

    FolderEntity findByName(String name);

    @Modifying
    @Transactional
    @Query("UPDATE FolderEntity f SET f.path=:path WHERE f.id=:id")
    void updatePathById(Long id, String path);

    @Transactional
    void removeById(Long folderId);

}
