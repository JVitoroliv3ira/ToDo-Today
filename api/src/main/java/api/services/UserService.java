package api.services;

import api.dtos.DetailsDTO;
import api.exceptions.BadRequestException;
import api.exceptions.NotFoundException;
import api.interfaces.crud.ICrudService;
import api.models.User;
import api.repositories.UserRepository;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class UserService implements ICrudService<User, Long, UserRepository>, UserDetailsService {
    @Getter
    private final UserRepository repository;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        User user = this.repository
                .findByEmail(email)
                .orElseThrow(() -> new NotFoundException(this.getEntityNotFoundMessage()));

        return new DetailsDTO(user);
    }

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
