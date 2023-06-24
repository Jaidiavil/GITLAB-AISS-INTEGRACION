package aiss.GitLabMiner.controller;

import aiss.GitLabMiner.model.Project;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.http.HttpStatus;

import org.springframework.web.bind.annotation.*;
import aiss.GitLabMiner.service.GitLabService;
import org.springframework.web.server.ResponseStatusException;

import javax.validation.Valid;

import java.util.List;

@RestController
@RequestMapping("/gitlab")
public class GitLabController {

    @Autowired
    GitLabService gitLabService;

    // GET http://localhost:8081/gitlab
    @GetMapping
    public List<Project> findAll(){
        return gitLabService.getProjects();
    }

    // GET http://localhost:8081/gitlab/{id}
    @GetMapping("/{id}")
    public Project findOne(@PathVariable String id,
                           @RequestParam(defaultValue = "2") Integer sinceCommits,
                           @RequestParam(defaultValue = "20") Integer sinceIssues,
                           @RequestParam(defaultValue = "2") Integer maxPages) {

        Project project = gitLabService.getProject(id, sinceCommits, sinceIssues, maxPages);

        if (project == null){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Project not found");
        } else {
            return project;
        }
    }

    // POST http://localhost:8081/gitlab/{id}
    @PostMapping("/{id}")
    @ResponseStatus(HttpStatus.CREATED)
    public Project createProject(@PathVariable @Valid String id,
                                 @RequestParam(defaultValue = "2") Integer sinceCommits,
                                 @RequestParam(defaultValue = "20") Integer sinceIssues,
                                 @RequestParam(defaultValue = "2") Integer maxPages) {

        Project project = gitLabService.getProject(id,sinceCommits,sinceIssues,maxPages);
        if (project == null){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Project not found");
        } else {
            return gitLabService.postProject(project);
        }
    }

}
