package aiss.GitLabMiner.controller;

import aiss.GitLabMiner.exceptions.ProjectNotFoundException;
import aiss.GitLabMiner.model.Project;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.http.HttpStatus;

import org.springframework.web.bind.annotation.*;
import aiss.GitLabMiner.Service.GitLabService;
import org.springframework.web.server.ResponseStatusException;

import javax.validation.Valid;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/gitlab")
public class GitLabController {

    @Autowired
    GitLabService gitLabService;

    @GetMapping
    public List<Project> findAll(){
        return gitLabService.getProjects();
    }
    /*
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
    */
    @PostMapping("/{id}")
    @ResponseStatus(HttpStatus.CREATED)
    public Project createProject(@PathVariable @Valid String id,
                                 @RequestParam(defaultValue = "2") Integer sinceCommits,
                                 @RequestParam(defaultValue = "20") Integer sinceIssues,
                                 @RequestParam(defaultValue = "2") Integer maxPages) {

        Project project = gitLabService.getProject(id,sinceCommits,sinceIssues,maxPages);
        if (project == null){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Projec not found");
        } else {
            return gitLabService.postProject(project);
        }
    }
    @GetMapping("/{id}")
    public Project errorId(@PathVariable String id,@RequestParam(defaultValue = "2") Integer sinceCommits,
                           @RequestParam(defaultValue = "20") Integer sinceIssues,
                           @RequestParam(defaultValue = "2") Integer maxPages) throws ProjectNotFoundException {
        Optional<Project> result = Optional.ofNullable(gitLabService.getProject(id, sinceCommits, sinceIssues, maxPages));
        if (!result.isPresent()) {
            throw new ProjectNotFoundException();
        }
        return result.get();
    }


}
