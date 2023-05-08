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

}
