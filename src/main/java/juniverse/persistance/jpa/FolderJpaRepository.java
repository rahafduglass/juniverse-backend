package juniverse.persistance.jpa;

import juniverse.persistance.entities.FolderEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface FolderJpaRepository extends JpaRepository<FolderEntity, Long> {

    FolderEntity findByName(String name);

}
