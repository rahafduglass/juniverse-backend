package juniverse.persistance.repositories.chat;

import juniverse.domain.models.chat.TherapistNoteModel;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface NoteRepository {
    boolean addNote(TherapistNoteModel therapistNoteModel);

    List<TherapistNoteModel> getNotes(Long chatId);

    boolean updateNote(TherapistNoteModel therapistNoteModel);

    boolean deleteMessage(Long noteId);
}
