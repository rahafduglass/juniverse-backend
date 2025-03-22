package juniverse.domain.mappers;


import juniverse.application.dtos.note.NoteRequest;
import juniverse.application.dtos.note.NoteResponse;
import juniverse.domain.models.NoteModel;
import juniverse.persistance.entities.NoteEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface NoteMapper {

    NoteModel requestToModel(NoteRequest noteRequest);

    @Mapping(source="privateChatId",target="privateChat.id")
    @Mapping(source="therapistId",target="therapist.id")
    NoteEntity modelToEntity(NoteModel noteModel);

    @Mapping(source="privateChat.id",target="privateChatId")
    @Mapping(source="therapist.id",target="therapistId")
    NoteModel entityToModel(NoteEntity noteEntity);

    NoteResponse modelToResponse(NoteModel noteModel);
}
