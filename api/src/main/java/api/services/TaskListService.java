package api.services;

import api.interfaces.crud.ICrudService;
import api.models.TaskList;
import api.models.User;
import api.repositories.TaskListRepository;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class TaskListService implements ICrudService<TaskList, Long, TaskListRepository> {
    @Getter
    private final TaskListRepository repository;

    public TaskList create(TaskList taskList, User creator) {
        this.associateCreatorWithTaskList(creator, taskList);
        return this.create(taskList);
    }

    public Page<TaskList> paginate(Long creatorId, Integer page, Integer pageSize) {
        Pageable pageable = PageRequest.of(page, pageSize);
        return this.repository.findAll(creatorId, pageable);
    }

    @Override
    public String getEntityNotFoundMessage() {
        return "Desculpe, não foi possível encontrar a lista de tarefas.";
    }

    private void associateCreatorWithTaskList(User creator, TaskList taskList) {
        taskList.setCreator(creator);
    }
}
