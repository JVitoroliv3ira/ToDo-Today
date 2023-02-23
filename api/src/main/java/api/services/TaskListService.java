package api.services;

import api.interfaces.crud.ICrudService;
import api.models.TaskList;
import api.repositories.TaskListRepository;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class TaskListService implements ICrudService<TaskList, Long, TaskListRepository> {
    @Getter
    private final TaskListRepository repository;

    @Override
    public String getEntityNotFoundMessage() {
        return "Desculpe, não foi possível encontrar a lista de tarefas.";
    }
}
