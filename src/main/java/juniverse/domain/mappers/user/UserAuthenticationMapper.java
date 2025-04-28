package juniverse.domain.mappers.user;


import juniverse.application.dtos.authentication.UserAuthenticationRequest;
import juniverse.application.dtos.authentication.UserAuthenticationResponse;
import juniverse.domain.models.user.UserAuthenticationModel;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface UserAuthenticationMapper {

    UserAuthenticationResponse modelToResponse(UserAuthenticationModel userAuthenticationModel);

    UserAuthenticationModel requestToModel(UserAuthenticationRequest userAuthenticationRequest);
}
