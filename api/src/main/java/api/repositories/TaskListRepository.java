package api.repositories;

import api.models.TaskList;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface TaskListRepository extends JpaRepository<TaskList, Long> {
    @Query("SELECT t FROM TaskList t WHERE t.creator.id = ?1")
    Page<TaskList> findAll(Long creatorId, Pageable pageable);
}
