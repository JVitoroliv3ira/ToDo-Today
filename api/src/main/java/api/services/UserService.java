package api.services;

import api.exceptions.BadRequestException;
import api.interfaces.crud.ICrudService;
import api.models.User;
import api.repositories.UserRepository;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@RequiredArgsConstructor
@Service
public class UserService implements ICrudService<User, Long, UserRepository> {
    @Getter
    private final UserRepository repository;

    public void validateThatEmailIsUnique(String email) {
        if (verifyThatEmailIsInUse(email)) {
            throw new BadRequestException(this.getEmailIsNotUniqueMessage());
        }
    }

    public String getEntityNotFoundMessage() {
        return "Desculpe, não foi possível encontrar o usuário procurado. Verifique se as informações estão corretas e tente novamente.";
    }

    public String getEmailIsNotUniqueMessage() {
        return "O email informado já está sendo utilizado.";
    }

    private boolean verifyThatEmailIsInUse(String email) {
        return Boolean.TRUE.equals(this.repository.existsByEmail(email));
    }

}
