package aiss.GitLabMiner.repository;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import aiss.GitLabMiner.model.*;
import org.springframework.stereotype.Repository;

@Repository
public class ProjectRepository implements InterfaceRepository {

    List<Project> projects = new ArrayList<>();

    //Creacion de datos de prueba temporales
    public ProjectRepository() {
        projects.add(new Project(
                UUID.randomUUID().toString(),
                "nombredeprueba1",
                "urldeprueba1"

        ));
        projects.add(new Project(
                UUID.randomUUID().toString(),
                "nombredeprueba2",
                "urldeprueba12"

        ));
    }

    public List<Project> findAll() {
        return projects;
    }

    public Project findOne(String id) {
        return projects.stream().filter(project -> project.getId().equals(id)).findFirst().orElse(null);
    }

    //Metodo para crear un nuevo proyecto
    public Project create(Project project){
        Project newProject = new Project(
                UUID.randomUUID().toString(),
                project.getName(),
                project.getWebUrl());
        projects.add(newProject);
        return newProject;
    }

    //Metodo para actualizar un proyecto existente
    public void update(Project updatedProject, String id){
        Project existing = findOne(id);
        int i = projects.indexOf(existing);
        updatedProject.setId(existing.getId());
        projects.set(i, updatedProject);
    }

    //Metodo para actualizar un proyecto existente
    public void delete(String id){
        projects.removeIf(project -> project.getId().equals(id));
    }

}
