package juniverse.persistance.adapter.dashboard;

import juniverse.domain.mappers.dashboard.TaskMapper;
import juniverse.domain.models.dashboard.TaskModel;
import juniverse.persistance.jpa.dashboard.TaskJpaRepository;
import juniverse.persistance.repositories.dashboard.TaskRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@RequiredArgsConstructor
public class TaskAdapter implements TaskRepository {

    private final TaskJpaRepository taskJpaRepository;
    private final TaskMapper taskMapper;

    @Override
    public boolean addTask(TaskModel task) {
        taskJpaRepository.save(taskMapper.modelToEntity(task));
        return true;
    }

    @Override
    public List<TaskModel> findTasksByOwnerId(Long ownerId) {
       return taskJpaRepository.findAllByOwnerId(ownerId).stream().map(taskMapper::entityToModel).toList();
    }

    @Override
    public boolean checkTask(Long taskId) {
        return taskJpaRepository.checkTask(taskId)>0;
    }

    @Override
    public boolean deleteTask(Long taskId) {
        taskJpaRepository.deleteById(taskId);
        return true;
    }

    @Override
    public boolean uncheckTask(Long taskId) {
        return taskJpaRepository.uncheckTask(taskId)>0;
    }


}
