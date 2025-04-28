package juniverse.domain.mappers.chat;


import juniverse.application.dtos.note.NoteRequest;
import juniverse.application.dtos.note.NoteResponse;
import juniverse.domain.models.chat.TherapistNoteModel;
import juniverse.persistance.entities.chat.TherapistNoteEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface TherapistNoteMapper {

    TherapistNoteModel requestToModel(NoteRequest noteRequest);

    @Mapping(source="privateChatId",target="privateChat.id")
    @Mapping(source="therapistId",target="therapist.id")
    TherapistNoteEntity modelToEntity(TherapistNoteModel therapistNoteModel);

    @Mapping(source="privateChat.id",target="privateChatId")
    @Mapping(source="therapist.id",target="therapistId")
    TherapistNoteModel entityToModel(TherapistNoteEntity therapistNoteEntity);

    NoteResponse modelToResponse(TherapistNoteModel therapistNoteModel);
}
