package juniverse.persistance.repositories;

import juniverse.domain.models.NoteModel;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface NoteRepository {
    boolean addNote(NoteModel noteModel);

    List<NoteModel> getNotes(Long chatId);

    boolean updateNote(NoteModel noteModel);

    boolean deleteMessage(Long noteId);
}
