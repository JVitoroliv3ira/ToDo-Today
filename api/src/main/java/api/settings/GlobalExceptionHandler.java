package api.settings;

import api.dtos.responses.ResponseDTO;
import api.exceptions.BadRequestException;
import org.springframework.context.support.DefaultMessageSourceResolvable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.List;

import static org.springframework.http.HttpStatus.BAD_REQUEST;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ResponseDTO<Object>> handleArgumentNotValidException(MethodArgumentNotValidException ex) {
        List<String> erros = ex.getBindingResult()
                .getFieldErrors()
                .stream().map(DefaultMessageSourceResolvable::getDefaultMessage)
                .toList();

        return ResponseEntity
                .status(BAD_REQUEST)
                .body(new ResponseDTO<>(null, null, erros));
    }

    @ExceptionHandler(BadRequestException.class)
    public ResponseEntity<ResponseDTO<Object>> handleBadRequestException(BadRequestException ex) {
        return ResponseEntity
                .status(BAD_REQUEST)
                .body(new ResponseDTO<>(null, null, List.of(ex.getMessage())));
    }
}
