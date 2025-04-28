package juniverse.persistance.repositories.dashboard;

import juniverse.domain.models.dashboard.TaskModel;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TaskRepository {
     boolean addTask(TaskModel task) ;

     List<TaskModel> findTasksByOwnerId(Long ownerId);

     boolean checkTask(Long taskId);

     boolean deleteTask(Long taskId);

    boolean uncheckTask(Long taskId);
}
