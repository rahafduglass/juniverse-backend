package juniverse.persistance.adapter;


import juniverse.domain.mappers.NoteMapper;
import juniverse.domain.models.NoteModel;
import juniverse.persistance.jpa.NoteJpaRepository;
import juniverse.persistance.repositories.NoteRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.stream.Collectors;

@Repository
@RequiredArgsConstructor
public class NoteAdapter implements NoteRepository {

    private final NoteJpaRepository noteJpaRepository;
    private final NoteMapper noteMapper;

    @Override
    public boolean addNote(NoteModel noteModel) {
        noteJpaRepository.save(noteMapper.modelToEntity(noteModel));
        return true;
    }

    @Override
    public List<NoteModel> getNotes(Long chatId) {
        return (noteJpaRepository.findAllByPrivateChatId(chatId)).stream().map(noteMapper::entityToModel).collect(Collectors.toList());
    }

    @Override
    public boolean updateNote(NoteModel noteModel) {
        return noteJpaRepository.updateNote(noteModel.getId(), noteModel.getTitle(), noteModel.getDescription()) > 0;
    }

    @Override
    public boolean deleteMessage(Long noteId) {
        noteJpaRepository.deleteById(noteId);
        return true;
    }
}
