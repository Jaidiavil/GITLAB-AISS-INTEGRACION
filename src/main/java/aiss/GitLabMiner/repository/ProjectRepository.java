package aiss.GitLabMiner.repository;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import aiss.GitLabMiner.model.*;

public class ProjectRepository {

    List<Project> projects = new ArrayList<>();

    public ProjectRepository(){
        projects.add(new Project(
        ));
    }

    public List<Project> findAll() {
        return projects;
    }
    public  Project findOne(String id) {
        return projects.stream().filter(project -> project.getId().equals(id)).findFirst().orElse(null);
    }
    public  Project create(Project project){
        Project newProject = new Project(
                UUID.randomUUID().toString(),
                project.getName(),
                project.getWebUrl());
        projects.add(project);
    return newProject;
    }
    public void update(Project updatedProject, String id){
        Project existing = findOne(id);
        int i = projects.indexOf(existing);
        updatedProject.setId(existing.getId());
        projects.set(i, updatedProject);
    }
}
