package api.controllers;

import api.dtos.requests.tasklist.TaskListCreationRequestDTO;
import api.dtos.responses.PageableDTO;
import api.dtos.responses.ResponseDTO;
import api.dtos.responses.TaskListResponseDTO;
import api.models.TaskList;
import api.services.AuthenticatedUserService;
import api.services.TaskListService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import static org.springframework.http.HttpStatus.CREATED;
import static org.springframework.http.HttpStatus.OK;

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

    @GetMapping(path = "/paginate")
    public ResponseEntity<ResponseDTO<PageableDTO<TaskListResponseDTO>>> paginate(@RequestParam Integer page, @RequestParam Integer pageSize) {
        Page<TaskList> result = this.taskListService.paginate(AuthenticatedUserService.getAuthenticatedUserId(), page, pageSize);
        return ResponseEntity
                .status(OK)
                .body(new ResponseDTO<>(new PageableDTO<>(result.map(TaskListResponseDTO::new)), null, null));
    }
}
