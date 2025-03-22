package juniverse.persistance.jpa;

import jakarta.transaction.Transactional;
import juniverse.domain.models.NoteModel;
import juniverse.persistance.entities.NoteEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface NoteJpaRepository extends JpaRepository<NoteEntity,Long> {

    List<NoteEntity> findAllByPrivateChatId(Long chatId);

    @Transactional
    @Modifying
    @Query("UPDATE note n SET n.title=:title ,n.description=:description WHERE n.id=:noteId")
    int updateNote(Long noteId, String title, String description);
}
