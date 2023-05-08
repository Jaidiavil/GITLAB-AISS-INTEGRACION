package aiss.GitLabMiner.Controller;
import aiss.GitLabMiner.model.Project;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import aiss.GitLabMiner.repository.ProjectRepository;

import java.util.List;

@RestController
@RequestMapping("/api/projects")
public class ProjectController {

    private ProjectRepository repository;
    public void ProjectRepository (ProjectRepository repository) {
        this.repository = repository;
    }

    public List<Project> findAll() {
        return repository.findAll();
    }


}
