package juniverse.domain.services.dashboard;

import juniverse.domain.models.dashboard.TaskModel;
import juniverse.domain.provider.IdentityProvider;
import juniverse.persistance.repositories.dashboard.TaskRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class TaskService {
    private final TaskRepository taskRepository;
    private final IdentityProvider identityProvider;

    public boolean addTask(String taskTitle) {
        TaskModel task= TaskModel.builder()
                .title(taskTitle)
                .owner(identityProvider.currentIdentity().getId())
                .isChecked(false)
                .build();

        return taskRepository.addTask(task);
    }

    public List<TaskModel> getTasks() {
        return taskRepository.findTasksByOwnerId(identityProvider.currentIdentity().getId());
    }

    public boolean checkTask(Long taskId) {
        return taskRepository.checkTask(taskId);
    }

    public boolean deleteTask(Long taskId) {
        return taskRepository.deleteTask(taskId);
    }


    public boolean uncheck(Long taskId) {
        return taskRepository.uncheckTask(taskId);
    }
}
