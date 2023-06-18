package aiss.GitLabMiner.controller;
import aiss.GitLabMiner.model.Comment;
import aiss.GitLabMiner.model.Commit;
import aiss.GitLabMiner.model.Issue;
import aiss.GitLabMiner.model.Project;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;
import aiss.GitLabMiner.service.GitLabService;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@RestController
@RequestMapping("/gitlab")
public class GitLabController {

    @Autowired
    RestTemplate restTemplate;

    @Autowired
    GitLabService gitLabService;

    @GetMapping
    public List<Project> findAll(){
        return gitLabService.getProjects();
    }

    @GetMapping("/{id}")
    public Project findOne(@PathVariable String id,
                           @RequestParam(defaultValue = "2") Integer sinceCommits,
                           @RequestParam(defaultValue = "20") Integer sinceIssues,
                           @RequestParam(defaultValue = "2") Integer maxPages) {

        Project project = gitLabService.getProject(id,sinceCommits,sinceIssues,maxPages);
        return project;
    }

    @PostMapping("/{id}")
    @ResponseStatus(HttpStatus.CREATED)
    public Project createProject(@PathVariable @Valid String id,
                                 @RequestParam(defaultValue = "2") Integer sinceCommits,
                                 @RequestParam(defaultValue = "20") Integer sinceIssues,
                                 @RequestParam(defaultValue = "2") Integer maxPages) {

        Project project = gitLabService.getProject(id,sinceCommits,sinceIssues,maxPages);
        return gitLabService.postProject(project);
    }

}
