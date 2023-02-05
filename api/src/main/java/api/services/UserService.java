package api.services;

import api.interfaces.crud.ICrudService;
import api.models.User;
import api.repositories.UserRepository;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class UserService implements ICrudService<User, Long, UserRepository> {
    @Getter
    private final UserRepository repository;

    public String getEntityNotFoundMessage() {
        return "Desculpe, não foi possível encontrar o usuário procurado. Verifique se as informações estão corretas e tente novamente.";
    }
}
