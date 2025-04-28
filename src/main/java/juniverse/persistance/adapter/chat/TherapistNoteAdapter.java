package juniverse.persistance.adapter.chat;


import juniverse.domain.mappers.chat.TherapistNoteMapper;
import juniverse.domain.models.chat.TherapistNoteModel;
import juniverse.persistance.jpa.chat.TherapistNoteJpaRepository;
import juniverse.persistance.repositories.chat.NoteRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.stream.Collectors;

@Repository
@RequiredArgsConstructor
public class TherapistNoteAdapter implements NoteRepository {

    private final TherapistNoteJpaRepository therapistNoteJpaRepository;
    private final TherapistNoteMapper therapistNoteMapper;

    @Override
    public boolean addNote(TherapistNoteModel therapistNoteModel) {
        therapistNoteJpaRepository.save(therapistNoteMapper.modelToEntity(therapistNoteModel));
        return true;
    }

    @Override
    public List<TherapistNoteModel> getNotes(Long chatId) {
        return (therapistNoteJpaRepository.findAllByPrivateChatId(chatId)).stream().map(therapistNoteMapper::entityToModel).collect(Collectors.toList());
    }

    @Override
    public boolean updateNote(TherapistNoteModel therapistNoteModel) {
        return therapistNoteJpaRepository.updateNote(therapistNoteModel.getId(), therapistNoteModel.getTitle(), therapistNoteModel.getDescription()) > 0;
    }

    @Override
    public boolean deleteMessage(Long noteId) {
        therapistNoteJpaRepository.deleteById(noteId);
        return true;
    }
}
