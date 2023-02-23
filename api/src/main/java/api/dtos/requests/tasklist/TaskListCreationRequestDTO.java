package api.dtos.requests.tasklist;

import api.models.TaskList;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class TaskListCreationRequestDTO {
    @NotNull(message = "É necessário informar um título para a lista de tarefas.")
    @Size(min = 5, max = 80, message = "O título da lista de tarefas deve conter entre {min} e {max} caracteres.")
    private String title;
    @Size(max = 300, message = "A descrição da lista de tarefas deve conter no máximo {max} caracteres.")
    private String description;

    public TaskList convert() {
        return TaskList
                .builder()
                .title(this.title)
                .description(this.description)
                .build();
    }
}
