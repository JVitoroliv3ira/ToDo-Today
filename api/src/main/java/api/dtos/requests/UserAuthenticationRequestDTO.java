package api.dtos.requests;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class UserAuthenticationRequestDTO {
    @NotNull(message = "É necessário informar um email.")
    @Size(min = 10, max = 90, message = "O email informado é inválido.")
    @Email(message = "O email informado é inválido.")
    private String email;
    @NotNull(message = "É necessário informar uma senha.")
    @Size(min = 6, max = 90, message = "A senha informada é inválida.")
    private String password;
}
