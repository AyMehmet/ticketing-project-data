package com.cydeo.service;

import com.cydeo.dto.TaskDTO;

import java.util.List;

public interface TaskService {

    List<TaskDTO> listAllTask();
    void save(TaskDTO taskDTO);
    void delete(Long id);
    TaskDTO findById(Long id);
    void update(TaskDTO taskDTO);
    List<TaskDTO> listAllNonCompletedTask();
    List<TaskDTO> listAllCompletedTask();

}
