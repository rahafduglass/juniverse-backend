package juniverse.domain.mappers;


import juniverse.application.dtos.authentication.UserAuthenticationRequest;
import juniverse.application.dtos.authentication.UserAuthenticationResponse;
import juniverse.domain.models.UserAuthenticationModel;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface UserAuthenticationMapper {

    UserAuthenticationResponse modelToResponse(UserAuthenticationModel userAuthenticationModel);

    UserAuthenticationModel requestToModel(UserAuthenticationRequest userAuthenticationRequest);
}
