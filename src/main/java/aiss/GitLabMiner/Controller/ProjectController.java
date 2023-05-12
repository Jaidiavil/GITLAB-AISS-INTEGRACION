package aiss.GitLabMiner.Controller;
import aiss.GitLabMiner.Service.GitLabService;
import aiss.GitLabMiner.model.Project;
import aiss.GitLabMiner.service.GitLabService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import aiss.GitLabMiner.repository.ProjectRepository;
import javax.validation.Valid;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/projects")
public class ProjectController {
    @Autowired
    GitLabService repository;

    public ProjectController(GitLabService repository) {
        this.repository = repository;
    }

    //GET http://localhost:8081/api/projects
    @GetMapping
    public List<Project> findAll() {
        return repository.findAll();
    }

    //GET http://localhost:8081/api/projects/{id}
    @GetMapping("/{id}")
    public Project findOne(@PathVariable long id) {
        Optional<Project> project = repository.findById(id);
        return project.get();
    }

    //Operacion de creacion
    //POST http://localhost:8081/api/projects
    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping
    public Project create(@Valid @RequestBody Project project) {
        Project _project = repository
                .createProject(new Project(project.getName(), project.getWebUrl()));
        return _project;
    }

    //Operacion de actualizacion
    //PUT http://localhost:8081/api/projects/{id}
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @PutMapping("/{id}")
    public void update(@PathVariable long id, @Valid @RequestBody Project updatedProject) {
        Optional<Project> projectData = repository.findById(id);

        Project _project = projectData.get();
        _project.setName(updatedProject.getName());
        _project.setWebUrl(updatedProject.getWebUrl());
        repository.createProject(_project);
    }

    //Operacion de eliminacion
    //DELETE http://localhost:8081/api/projects/{id}
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @DeleteMapping
    public void delete(@RequestParam long id) {
       if (repository.existsById(id)) {
           repository.deleteById(id);
       }
    }
}
