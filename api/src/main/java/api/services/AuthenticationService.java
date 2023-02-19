package api.services;

import api.exceptions.BadRequestException;
import api.interfaces.utils.IPasswordUtils;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;

import java.security.Key;
import java.util.Date;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

@Service
public class AuthenticationService {

    private final String SECRET_KEY;

    public AuthenticationService(@Value("${jwt.token.secret}") String SECRET_KEY) {
        this.SECRET_KEY = SECRET_KEY;
    }

    public String generateToken(UserDetails details) {
        return Jwts.builder()
                    .setHeaderParam("typ", "JWT")
                    .setSubject(details.getUsername())
                    .setIssuedAt(new Date(System.currentTimeMillis()))
                    .setExpiration(new Date(System.currentTimeMillis() + 1000 * 60 * 30))
                    .signWith(this.getSignInKey())
                    .compact();
    }

    public void validatePasswordMatch(String rawPassword, String encodedPassword) {
        if(Boolean.FALSE.equals(this.comparePasswords(rawPassword, encodedPassword))) {
            throw new BadRequestException("Desculpe, suas credenciais estão incorretas. Verifique seu email e senha e tente novamente.");
        }
    }

    private Boolean comparePasswords(String rawPassword, String encodedPassword) {
        return IPasswordUtils.matches(rawPassword, encodedPassword);
    }

    private Key getSignInKey() {
        byte[] bytes = Decoders.BASE64.decode(this.SECRET_KEY);
        return Keys.hmacShaKeyFor(bytes);
    }
}
