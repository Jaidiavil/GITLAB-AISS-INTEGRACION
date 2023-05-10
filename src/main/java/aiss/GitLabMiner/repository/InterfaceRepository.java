package aiss.GitLabMiner.repository;

import aiss.GitLabMiner.model.Project;

import java.util.List;

public interface InterfaceRepository {
    List<Project> findAll();
    Project findOne(String id);
    Project create(Project project);
    void update(Project updatedProject, String id);
    void delete(String id);
}
