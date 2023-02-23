package api.controllers;

import api.dtos.requests.tasklist.TaskListCreationRequestDTO;
import api.dtos.responses.ResponseDTO;
import api.dtos.responses.TaskListResponseDTO;
import api.models.TaskList;
import api.services.AuthenticatedUserService;
import api.services.TaskListService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import static org.springframework.http.HttpStatus.CREATED;

@RequiredArgsConstructor
@RequestMapping(path = "/api/v1/task-list")
@RestController
public class TaskListController {
    private final TaskListService taskListService;

    @PostMapping(path = "/create")
    public ResponseEntity<ResponseDTO<TaskListResponseDTO>> create(@Valid @RequestBody TaskListCreationRequestDTO request) {
        TaskList result = this.taskListService.create(request.convert(), AuthenticatedUserService.getAuthenticatedUser());
        return ResponseEntity
                .status(CREATED)
                .body(new ResponseDTO<>(new TaskListResponseDTO(result), null, null));
    }
}
