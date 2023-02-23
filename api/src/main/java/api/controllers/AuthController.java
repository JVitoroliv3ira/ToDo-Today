package api.controllers;

import api.dtos.DetailsDTO;
import api.dtos.requests.user.UserAuthenticationRequestDTO;
import api.dtos.requests.user.UserRegisterRequestDTO;
import api.dtos.responses.AuthenticationResponseDTO;
import api.dtos.responses.ResponseDTO;
import api.models.User;
import api.services.AuthenticationService;
import api.services.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import static org.springframework.http.HttpStatus.CREATED;
import static org.springframework.http.HttpStatus.OK;

@RequiredArgsConstructor
@RequestMapping(path = "/api/v1/auth")
@RestController
public class AuthController {
    private final UserService userService;
    private final AuthenticationService authenticationService;

    @PostMapping(path = "/register")
    public ResponseEntity<ResponseDTO<AuthenticationResponseDTO>> register(@Valid @RequestBody UserRegisterRequestDTO request) {
        this.userService.validateThatEmailIsUnique(request.getEmail());
        User user = this.userService.create(request.convert());
        UserDetails details = new DetailsDTO(user);
        String token = this.authenticationService.generateToken(details);

        return ResponseEntity
                .status(CREATED)
                .body(new ResponseDTO<>(new AuthenticationResponseDTO(details, token), null, null));
    }

    @PostMapping(path = "/login")
    public ResponseEntity<ResponseDTO<AuthenticationResponseDTO>> login(@Valid @RequestBody UserAuthenticationRequestDTO request) {
        UserDetails details = this.userService.loadUserByUsername(request.getEmail());
        this.authenticationService.validatePasswordMatch(request.getPassword(), details.getPassword());
        String token = this.authenticationService.generateToken(details);
        return ResponseEntity
                .status(OK)
                .body(new ResponseDTO<>(new AuthenticationResponseDTO(details, token), null, null));
    }
}
