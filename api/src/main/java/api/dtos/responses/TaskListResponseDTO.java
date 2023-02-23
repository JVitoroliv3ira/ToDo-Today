package api.dtos.responses;

import api.models.TaskList;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class TaskListResponseDTO {
    public TaskListResponseDTO(TaskList taskList) {
        this.id = taskList.getId();
        this.title = taskList.getTitle();
        this.description = taskList.getDescription();
    }
    private Long id;
    private String title;
    private String description;
}
