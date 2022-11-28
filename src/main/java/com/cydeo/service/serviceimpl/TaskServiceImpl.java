package com.cydeo.service.serviceimpl;

import com.cydeo.dto.TaskDTO;
import com.cydeo.entity.Task;
import com.cydeo.enums.Status;
import com.cydeo.mapper.TaskMapper;
import com.cydeo.repository.TaskRepository;
import com.cydeo.service.TaskService;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class TaskServiceImpl implements TaskService {

    private final TaskRepository taskRepository;
    private final TaskMapper taskMapper;

    public TaskServiceImpl(TaskRepository taskRepository, ModelMapper modelMapper, TaskMapper taskMapper) {
        this.taskRepository = taskRepository;
        this.taskMapper = taskMapper;

    }

    @Override
    public List<TaskDTO> listAllTask() {
        return taskRepository.findAll().stream().map(taskMapper::convertToDto).collect(Collectors.toList());
    }

    @Override
    public void save(TaskDTO taskDTO) {
        taskDTO.setTaskStatus(Status.OPEN);
        taskDTO.setAssignedDate(LocalDate.now());
        Task task = taskMapper.convertToEntity(taskDTO);
        taskRepository.save(task);
    }

    @Override
    public void delete(Long id) {
        Task deletedTask=taskRepository.findTasksById(id);
        deletedTask.setIsDeleted(true);
        taskRepository.save(deletedTask);
    }

    @Override
    public TaskDTO findById(Long id) {
        return taskMapper.convertToDto(taskRepository.findTasksById(id));
    }

    @Override
    public void update(TaskDTO taskDTO) {

        Task currentTask=taskRepository.findTasksById(taskDTO.getId());
        Task updatedTask=taskMapper.convertToEntity(taskDTO);
        updatedTask.setId(currentTask.getId());
        updatedTask.setTaskStatus(currentTask.getTaskStatus());
        updatedTask.setProject(currentTask.getProject());
        taskRepository.save(updatedTask);
    }

    @Override
    public List<TaskDTO> listAllNonCompletedTask() {
        return taskRepository.findAll().stream().filter(task -> (task.getTaskStatus().equals((Status.OPEN)) || task.getTaskStatus().equals((Status.IN_PROGRESS)))).map(taskMapper::convertToDto).collect(Collectors.toList());
//        return taskRepository.findTasksByTaskStatusIsNot(Status.COMPLETE).stream().map(taskMapper::convertToDto).collect(Collectors.toList());
    }

    @Override
    public List<TaskDTO> listAllCompletedTask() {
        return taskRepository.findAll().stream().filter(task -> task.getTaskStatus().equals(Status.COMPLETE)).map(taskMapper::convertToDto).collect(Collectors.toList());
    }

    @Override
    public int totalNonCompletedTask(String projectCode) {
        return (int)listAllNonCompletedTask().stream().filter(taskDTO ->taskDTO.getProject().getProjectCode().equals(projectCode)).count();
    }

    @Override
    public int totalCompletedTask(String projectCode) {
        return (int)listAllCompletedTask().stream().filter(taskDTO ->taskDTO.getProject().getProjectCode().equals(projectCode)).count();

    }

}
