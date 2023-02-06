package api.controllers;

import api.dtos.requests.UserRegisterRequestDTO;
import api.dtos.responses.ResponseDTO;
import api.exceptions.BadRequestException;
import api.exceptions.NotFoundException;
import api.models.User;
import api.services.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

import static org.springframework.http.HttpStatus.BAD_REQUEST;
import static org.springframework.http.HttpStatus.CREATED;

@RequiredArgsConstructor
@RequestMapping(path = "/api/v1/auth")
@RestController
public class AuthController {
    private final UserService userService;

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
}
