package aiss.GitLabMiner.controller;
import aiss.GitLabMiner.model.Project;
import aiss.GitLabMiner.service.GitLabService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api/projects")
public class ProjectController {
    private final GitLabService service;

    public ProjectController(GitLabService service) {
        this.service = service;
    }

    //GET http://localhost:8081/api/projects
    @GetMapping
    public List<Project> findAll() {
        return service.getProjects();
    }

    //GET http://localhost:8081/api/projects/{id}
    @GetMapping("/{id}")
    public Project findOne(@PathVariable String id){
        return service.getProjectById(id);
    }

    //Operacion de creacion
    //POST http://localhost:8081/api/projects
    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping
    public Project create(@Valid @RequestBody Project project) {
        return service.postProject(project);
    }
}
