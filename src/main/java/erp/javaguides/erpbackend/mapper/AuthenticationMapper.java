package erp.javaguides.erpbackend.mapper;

import erp.javaguides.erpbackend.dto.AuthenticationDto;
import erp.javaguides.erpbackend.entity.Authentication;

public class AuthenticationMapper {

    public static AuthenticationDto mapToAuthenticationDto(Authentication authentication){
        return new AuthenticationDto(
                authentication.getId(),
                authentication.getEmail_Id(),
                authentication.getPassword()
        );
    }

    public static Authentication mapToAuthentication(AuthenticationDto authenticationDto){
        return new Authentication(
                authenticationDto.getId(),
                authenticationDto.getEmail_Id(),
                authenticationDto.getPassword()
        );
    }
}