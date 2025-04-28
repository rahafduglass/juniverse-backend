package juniverse.persistance.jpa.chat;

import jakarta.transaction.Transactional;
import juniverse.persistance.entities.chat.TherapistNoteEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TherapistNoteJpaRepository extends JpaRepository<TherapistNoteEntity,Long> {

    List<TherapistNoteEntity> findAllByPrivateChatId(Long chatId);

    @Transactional
    @Modifying
    @Query("UPDATE note n SET n.title=:title ,n.description=:description WHERE n.id=:noteId")
    int updateNote(Long noteId, String title, String description);
}
