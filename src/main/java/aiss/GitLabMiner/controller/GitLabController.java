package aiss.GitLabMiner.controller;
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
import aiss.GitLabMiner.Service.GitLabService;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/gitlabminer")
public class GitLabController {

    @Autowired
    RestTemplate restTemplate;

    @Autowired
    GitLabService gitLabService;

    final String gitMinerUrl = "http://localhost:8080/gitminer";

    //GET http://localhost:8081/gitlabminer/{id}
    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping("/{id}")
    public Project create(@PathVariable String id) {
        String url = "http://localhost:8080/gitminer/projects";

        Project project = gitLabService.getProject(id);
        Project createdProject = new Project(project.getId(), project.getName(), project.getWebUrl(), project.getCommits(), project.getIssues());
        Project result = restTemplate.postForObject(url, createdProject, Project.class);
        return result;
    }

    //GET http://localhost:8081/gitlabminer/{id}
    @GetMapping("/{id}")
    public Project findProject(@PathVariable String id) {
        Project project = gitLabService.getProject(id);
        List<Commit> commits = project.getCommits();
        List<Issue> issues = project.getIssues();
        Project filtredProject = new Project(project.getId(), project.getName(), project.getWebUrl(), commits, issues);
        return filtredProject;
    }

}
