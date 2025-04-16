package juniverse.persistance.jpa;

import jakarta.transaction.Transactional;
import juniverse.persistance.entities.TaskEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TaskJpaRepository extends JpaRepository<TaskEntity,Long> {

    @Query("SELECT t FROM task t WHERE t.owner.id=:ownerId")
    List<TaskEntity> findAllByOwnerId(Long ownerId);

    @Modifying
    @Transactional
    @Query("UPDATE task t SET t.isChecked=true WHERE t.id=:taskId")
    Integer checkTask(Long taskId);

    @Modifying
    @Transactional
    @Query("UPDATE task t SET t.isChecked=false WHERE t.id=:taskId")
    Integer uncheckTask(Long taskId);
}
