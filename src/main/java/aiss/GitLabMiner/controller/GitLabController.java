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
@RequestMapping("/gitlabminer")
public class GitLabController {

    @Autowired
    RestTemplate restTemplate;

    @Autowired
    GitLabService gitLabService;


    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping("/{id}")
    public Project create(@PathVariable String id,
                          @RequestParam(defaultValue = "20") Integer sinceIssues,
                          @RequestParam(defaultValue = "2") Integer sinceCommits,
                          @RequestParam(defaultValue = "2") Integer maxPages) {

        Project project = gitLabService.getProject(id);

        List<Commit> commits = gitLabService.getCommits(id, sinceCommits, maxPages);
        project.setCommits(commits);

        List<Issue> issues = gitLabService.getIssues(id, sinceIssues, maxPages);
        project.setIssues(issues);

        for (Issue i: issues){
            List<Comment> comments = gitLabService.getIssueComments(id,i.getRefId(),maxPages);
            i.setComments(comments);
        }

        gitLabService.postProject(project);

        return project;
    }

}
