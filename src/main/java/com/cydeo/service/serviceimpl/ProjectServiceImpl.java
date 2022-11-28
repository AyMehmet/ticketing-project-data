package com.cydeo.service.serviceimpl;

import com.cydeo.dto.ProjectDTO;
import com.cydeo.dto.UserDTO;
import com.cydeo.entity.Project;
import com.cydeo.entity.User;
import com.cydeo.enums.Status;
import com.cydeo.mapper.ProjectMapper;
import com.cydeo.mapper.UserMapper;
import com.cydeo.repository.ProjectRepository;
import com.cydeo.service.ProjectService;
import com.cydeo.service.TaskService;
import com.cydeo.service.UserService;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ProjectServiceImpl implements ProjectService {

    private final ProjectRepository projectRepository;
    private final ProjectMapper projectMapper;
    private final UserService userService;
    private final UserMapper userMapper;
    private final TaskService taskService;

    public ProjectServiceImpl(ProjectRepository projectRepository, ProjectMapper projectMapper, UserService userService, UserMapper userMapper, TaskService taskService) {
        this.projectRepository = projectRepository;
        this.projectMapper = projectMapper;
        this.userService = userService;
        this.userMapper = userMapper;
        this.taskService = taskService;
    }

    @Override
    public ProjectDTO getByProjectCode(String code) {
        return projectMapper.convertToDto(projectRepository.findProjectByProjectCodeIgnoreCase(code));
    }

    @Override
    public List<ProjectDTO> listAllProjects() {
        return projectRepository.findAll(Sort.by("projectCode")).stream().map(projectMapper::convertToDto).collect(Collectors.toList());
    }

    @Override
    public void save(ProjectDTO projectDTO) {

        projectDTO.setProjectStatus(Status.OPEN);
        Project project=projectMapper.convertToEntity(projectDTO);

       projectRepository.save(project);

    }

    @Override
    public void update(ProjectDTO dto) {
       Project currentProject=projectRepository.findProjectByProjectCodeIgnoreCase(dto.getProjectCode());
       Project updatedProject=projectMapper.convertToEntity(dto);
       updatedProject.setId(currentProject.getId());
       updatedProject.setProjectStatus(currentProject.getProjectStatus());
       projectRepository.save(updatedProject);

    }

    @Override
    public void delete(String code) {
        Project deletedProject=projectRepository.findProjectByProjectCodeIgnoreCase(code);
        deletedProject.setIsDeleted(true);
        projectRepository.save(deletedProject);

    }

    @Override
    public void complete(String projectCode) {
        Project currentProject=projectRepository.findProjectByProjectCodeIgnoreCase(projectCode);
        currentProject.setProjectStatus(Status.COMPLETE);
        projectRepository.save(currentProject);

    }

    @Override
    public List<ProjectDTO> listAllProjectDetails() {

        UserDTO currentUserDTO = userService.findbyID("harold@manager.com");
        User user=userMapper.convertToEntity(currentUserDTO);

//      List<Project> projectList=projectRepository.findAll().stream().filter(project -> project.getAssignedManager().getUserName().equals("harold@manager.com")).collect(Collectors.toList());

        List<Project> projectList=projectRepository.findProjectsByAssignedManager(user);
        return projectList.stream().map(project -> {

            ProjectDTO obj=projectMapper.convertToDto(project);

            obj.setUnfinishedTaskCounts(taskService.totalNonCompletedTask(project.getProjectCode()));
            obj.setCompleteTaskCounts(taskService.totalCompletedTask(project.getProjectCode()));

            return obj;
        }).collect(Collectors.toList());
    }
}
