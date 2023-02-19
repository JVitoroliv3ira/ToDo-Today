package api.controllers;

import api.dtos.requests.UserAuthenticationRequestDTO;
import api.dtos.requests.UserRegisterRequestDTO;
import api.dtos.responses.AuthenticationResponseDTO;
import api.dtos.responses.ResponseDTO;
import api.exceptions.BadRequestException;
import api.exceptions.NotFoundException;
import api.models.User;
import api.services.AuthenticationService;
import api.services.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

import static org.springframework.http.HttpStatus.*;

@RequiredArgsConstructor
@RequestMapping(path = "/api/v1/auth")
@RestController
public class AuthController {
    private final UserService userService;
    private final AuthenticationService authenticationService;

    @PostMapping(path = "/register")
    public ResponseEntity<ResponseDTO<User>> register(@Valid @RequestBody UserRegisterRequestDTO request) {
        try {
            this.userService.validateThatEmailIsUnique(request.getEmail());
            User user = this.userService.create(request.convert());
            
            return ResponseEntity
                    .status(CREATED)
                    .body(new ResponseDTO<>(user, null, null));
        } catch (BadRequestException e) {
            return ResponseEntity
                    .status(BAD_REQUEST)
                    .body(new ResponseDTO<>(null, null, List.of(e.getMessage())));
        }
    }

    @PostMapping(path = "/login")
    public ResponseEntity<ResponseDTO<AuthenticationResponseDTO>> login(@Valid @RequestBody UserAuthenticationRequestDTO request) {
        try {
            UserDetails details = this.userService.loadUserByUsername(request.getEmail());
            this.authenticationService.validatePasswordMatch(request.getPassword(), details.getPassword());
            String token = this.authenticationService.generateToken(details);
            return ResponseEntity
                    .status(OK)
                    .body(new ResponseDTO<>(new AuthenticationResponseDTO(details, token), null, null));
        } catch (NotFoundException | BadRequestException e) {
            HttpStatus status = e instanceof BadRequestException ? BAD_REQUEST : NOT_FOUND;
            return ResponseEntity
                    .status(status)
                    .body(new ResponseDTO<>(null, null, List.of(e.getMessage())));
        }
    }
}
