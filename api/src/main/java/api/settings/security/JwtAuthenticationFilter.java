
package api.settings.security;

import java.io.IOException;
import java.util.Optional;

import org.springframework.http.HttpHeaders;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import api.services.AuthenticationService;
import api.services.UserService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Component
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    private final UserService userService;
    private final AuthenticationService authenticationService;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        Optional<String> authorization = Optional.ofNullable(request.getHeader(HttpHeaders.AUTHORIZATION));
        if(Boolean.TRUE.equals(authorization.isPresent())) {
            String token = this.getAuthorizationToken(authorization.get());
            Optional<String> email = Optional.ofNullable(this.authenticationService.extractEmailFromToken(token));
            if(Boolean.TRUE.equals(email.isPresent())) {
                UserDetails details = this.userService.loadUserByUsername(email.get());
                if(Boolean.TRUE.equals(this.authenticationService.isTokenValid(token, details))) {
                    UsernamePasswordAuthenticationToken usernamePasswordAuthentication = new UsernamePasswordAuthenticationToken(
                        details,
                        null,
                        details.getAuthorities()
                    );
                    usernamePasswordAuthentication.setDetails(
                        new WebAuthenticationDetailsSource().buildDetails(request)
                    );
                    SecurityContextHolder.getContext().setAuthentication(usernamePasswordAuthentication);
                }
            }
        }

        filterChain.doFilter(request, response);    
    }

    private String getAuthorizationToken(String authorization) {
        return authorization.replace("Bearer ", "").trim();
    } 

}
