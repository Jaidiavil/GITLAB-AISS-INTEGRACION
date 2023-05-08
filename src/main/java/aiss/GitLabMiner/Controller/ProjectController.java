package aiss.GitLabMiner.Controller;
import aiss.GitLabMiner.model.Project;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import aiss.GitLabMiner.repository.ProjectRepository;

import java.util.List;

@RestController
@RequestMapping("/api/projects")
public class ProjectController {
    private final ProjectRepository repository;

    public ProjectController(ProjectRepository repository) {
        this.repository = repository;
    }

    //GET http://localhost:8080/api/projects
    @GetMapping
    public List<Project> findAll() {
        return repository.findAll();
    }


}
