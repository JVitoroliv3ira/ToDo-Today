package api.services;

import api.exceptions.BadRequestException;
import api.interfaces.utils.IPasswordUtils;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;

import java.security.Key;
import java.util.Date;
import java.util.function.Function;

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

    public String extractEmailFromToken(String token) {
        return this.extractClaim(token, Claims::getSubject);
    }

    public Boolean isTokenValid(String token, UserDetails details) {
        final String email = extractEmailFromToken(token);
        return (email.equals(details.getUsername())) && !isTokenExpired(token);
    }

    public void validatePasswordMatch(String rawPassword, String encodedPassword) {
        if(Boolean.FALSE.equals(this.comparePasswords(rawPassword, encodedPassword))) {
            throw new BadRequestException("Desculpe, suas credenciais estão incorretas. Verifique seu email e senha e tente novamente.");
        }
    }

    private Boolean comparePasswords(String rawPassword, String encodedPassword) {
        return IPasswordUtils.matches(rawPassword, encodedPassword);
    }

    private <T> T extractClaim(String token, Function<Claims, T> claimsResolver) {
        final Claims claims = extractAllClaims(token);
        return claimsResolver.apply(claims);
    }
    
    private Date extractExpiration(String token) {
        return extractClaim(token, Claims::getExpiration);
    }

    private boolean isTokenExpired(String token) {
        return extractExpiration(token).before(new Date());
    }

    private Claims extractAllClaims(String token) {
        return Jwts
                .parserBuilder()
                .setSigningKey(getSignInKey())
                .build()
                .parseClaimsJws(token)
                .getBody();
    }

    private Key getSignInKey() {
        byte[] bytes = Decoders.BASE64.decode(this.SECRET_KEY);
        return Keys.hmacShaKeyFor(bytes);
    }
}
