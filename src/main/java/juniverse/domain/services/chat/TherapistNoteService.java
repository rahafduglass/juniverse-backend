package juniverse.domain.services.chat;


import juniverse.domain.mappers.chat.TherapistNoteMapper;
import juniverse.domain.models.chat.TherapistNoteModel;
import juniverse.domain.provider.IdentityProvider;
import juniverse.persistance.repositories.chat.NoteRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class TherapistNoteService {

    private final NoteRepository noteRepository;
    private final TherapistNoteMapper therapistNoteMapper;
    private final IdentityProvider identityProvider;

    public boolean addNote(TherapistNoteModel therapistNoteModel) {
        therapistNoteModel.setTherapistId(identityProvider.currentIdentity().getId());
        return noteRepository.addNote(therapistNoteModel);
    }

    public List<TherapistNoteModel> getNotes(Long chatId) {
        return noteRepository.getNotes(chatId);
    }

    public boolean updateNote(Long noteId, String title, String description) {
        TherapistNoteModel therapistNoteModel = TherapistNoteModel.builder()
                .id(noteId)
                .title(title)
                .description(description)
                .build();
       return noteRepository.updateNote(therapistNoteModel);
    }

    public boolean deleteNote(Long noteId) {
        return noteRepository.deleteMessage(noteId);
    }
}
