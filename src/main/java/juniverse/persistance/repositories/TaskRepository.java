package juniverse.persistance.repositories;

import juniverse.domain.models.TaskModel;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TaskRepository {
     boolean addTask(TaskModel task) ;

     List<TaskModel> findTasksByOwnerId(Long ownerId);

     boolean checkTask(Long taskId);

     boolean deleteTask(Long taskId);
}
