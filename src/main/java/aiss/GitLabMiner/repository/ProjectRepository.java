package aiss.GitLabMiner.repository;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import aiss.GitLabMiner.model.*;
import org.springframework.stereotype.Repository;

@Repository
public class ProjectRepository {

    List<Project> projects = new ArrayList<>();

    public ProjectRepository(){
    }

    public List<Project> findAll() {
        return projects;
    }

    public Project findOne(String id) {
        return projects.stream().filter(project -> project.getId().equals(id)).findFirst().orElse(null);
    }

    public void delete(String id){
        projects.removeIf(project -> project.getId().equals(id));
    }

    public Project create(Project project){
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
