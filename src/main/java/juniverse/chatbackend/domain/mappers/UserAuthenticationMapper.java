package juniverse.chatbackend.domain.mappers;


import juniverse.chatbackend.application.dtos.authentication.UserAuthenticationRequest;
import juniverse.chatbackend.application.dtos.authentication.UserAuthenticationResponse;
import juniverse.chatbackend.domain.models.UserAuthenticationModel;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface UserAuthenticationMapper {

    public UserAuthenticationResponse modelToResponse(UserAuthenticationModel userAuthenticationModel);

    UserAuthenticationModel requestToModel(UserAuthenticationRequest userAuthenticationRequest);
}
