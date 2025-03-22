package juniverse.domain.services;


import io.micrometer.common.KeyValues;
import juniverse.domain.mappers.NoteMapper;
import juniverse.domain.models.NoteModel;
import juniverse.domain.provider.IdentityProvider;
import juniverse.persistance.repositories.NoteRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class NoteService {

    private final NoteRepository noteRepository;
    private final NoteMapper noteMapper;
    private final IdentityProvider identityProvider;

    public boolean addNote(NoteModel noteModel) {
        noteModel.setTherapistId(identityProvider.currentIdentity().getId());
        return noteRepository.addNote(noteModel);
    }

    public List<NoteModel> getNotes(Long chatId) {
        return noteRepository.getNotes(chatId);
    }

    public boolean updateNote(Long noteId, String title, String description) {
        NoteModel noteModel = NoteModel.builder()
                .id(noteId)
                .title(title)
                .description(description)
                .build();
       return noteRepository.updateNote(noteModel);
    }

    public boolean deleteNote(Long noteId) {
        return noteRepository.deleteMessage(noteId);
    }
}
