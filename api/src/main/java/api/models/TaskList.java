package api.models;


import api.interfaces.utils.IEntity;
import jakarta.persistence.*;
import lombok.*;

import java.util.Objects;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Table(schema = "TODOTODAY", name = "TB_TASKS_LISTS")
@Entity
public class TaskList implements IEntity<Long> {
    @Id
    @SequenceGenerator(name = "SQ_TASKS_LISTS", schema = "TODOTODAY", sequenceName = "SQ_TASKS_LISTS", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SQ_TASKS_LISTS")
    @Column(name = "id")
    private Long id;
    @Column(name = "TITLE")
    private String title;
    @Column(name = "DESCRIPTION")
    private String description;
    @ManyToOne
    @JoinColumn(name = "id")
    private User creator;


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        TaskList taskList = (TaskList) o;
        return Objects.equals(id, taskList.id) && Objects.equals(title, taskList.title) && Objects.equals(description, taskList.description) && Objects.equals(creator, taskList.creator);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, title, description, creator);
    }
}
