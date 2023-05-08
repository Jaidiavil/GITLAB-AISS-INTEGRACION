package aiss.GitLabMiner.Controller;
import aiss.GitLabMiner.model.Project;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import aiss.GitLabMiner.repository.ProjectRepository;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api/projects")
public class ProjectController {

    private ProjectRepository repository;
    public void ProjectRepository (ProjectRepository repository) {
        this.repository = repository;
    }
    @ResponseStatus(HttpStatus.ACCEPTED)
    @GetMapping
    public List<Project> findAll() {
        return repository.findAll();
    }

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping
    public Project create(@Valid @RequestBody Project project){
        return repository.create(project);
    }

}
