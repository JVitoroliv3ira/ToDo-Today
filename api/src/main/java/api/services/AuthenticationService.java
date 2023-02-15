package api.services;

import api.exceptions.BadRequestException;
import api.interfaces.utils.IPasswordUtils;
import org.springframework.stereotype.Service;

@Service
public class AuthenticationService {

    public void validatePasswordMatch(String rawPassword, String encodedPassword) {
        if(Boolean.FALSE.equals(this.comparePasswords(rawPassword, encodedPassword))) {
            throw new BadRequestException("Desculpe, suas credenciais estão incorretas. Verifique seu email e senha e tente novamente.");
        }
    }

    private Boolean comparePasswords(String rawPassword, String encodedPassword) {
        return IPasswordUtils.matches(rawPassword, encodedPassword);
    }
}
