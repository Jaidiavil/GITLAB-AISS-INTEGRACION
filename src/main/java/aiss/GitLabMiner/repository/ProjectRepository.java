package aiss.GitLabMiner.repository;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import aiss.GitLabMiner.model.*;
import org.springframework.web.bind.annotation.GetMapping;

public class ProjectRepository {

    List<Project> projects = new ArrayList<>();

    public ProjectRepository(){
        projects.add(new Project(
        ));
    }

    public List<Project> findAll() {
        return projects;
    }

    public  Project create(Project project){
        Project newProject = new Project(
                UUID.randomUUID().toString(),
                project.getName(),
                project.getWebUrl());
        projects.add(newProject);
    return newProject;
    }

}
