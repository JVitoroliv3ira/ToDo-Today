package api.dtos.responses;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.security.core.userdetails.UserDetails;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class AuthenticationResponseDTO {
    UserDetails details;
    String token;
}
