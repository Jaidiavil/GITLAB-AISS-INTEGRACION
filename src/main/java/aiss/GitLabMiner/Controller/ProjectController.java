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
    private final ProjectRepository repository;

    public ProjectController(ProjectRepository repository) {
        this.repository = repository;
    }

    //GET http://localhost:8080/api/projects
    @ResponseStatus(HttpStatus.ACCEPTED)
    @GetMapping
    public List<Project> findAll() {
        return repository.findAll();
    }

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping
    public Project create(@Valid @RequestBody Project project) {
        return repository.create(project);
    }


    @ResponseStatus(HttpStatus.NO_CONTENT)
    @PutMapping("/{id}")
    public void update(@PathVariable String id, @Valid @RequestBody Project updatedProject) {
        repository.update(updatedProject, id);
    }

    @ResponseStatus(HttpStatus.NO_CONTENT)
    @DeleteMapping("/{id}")
    public void delete(@PathVariable String id) {
        repository.delete(id);
    }
}
