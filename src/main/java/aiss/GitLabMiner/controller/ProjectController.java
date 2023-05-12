package aiss.GitLabMiner.controller;
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

    //GET http://localhost:8081/api/projects
    @GetMapping
    public List<Project> findAll() {
        return repository.findAll();
    }

    //GET http://localhost:8081/api/projects/{id}
    @GetMapping("/{id}")
    public Project findOne(@PathVariable String id){
        return repository.findOne(id);
    }

    //Operacion de creacion
    //POST http://localhost:8081/api/projects
    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping
    public Project create(@Valid @RequestBody Project project) {
        return repository.create(project);
    }

    //Operacion de actualizacion
    //PUT http://localhost:8081/api/projects/{id}
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @PutMapping("/{id}")
    public void update(@PathVariable String id, @Valid @RequestBody Project updatedProject) {
        repository.update(updatedProject, id);
    }

    //Operacion de eliminacion
    //DELETE http://localhost:8081/api/projects/{id}
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @DeleteMapping
    public void delete(@RequestParam String id) {
        repository.delete(id);
    }
}
