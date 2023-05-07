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
}
