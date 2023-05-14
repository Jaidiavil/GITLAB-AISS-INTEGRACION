package aiss.GitLabMiner.Service;

import aiss.GitLabMiner.model.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Service
public class GitLabService {

    @Autowired
    RestTemplate restTemplate;

    public List<User> findUsers(){

        String token = "glpat-3Ty1dCruj56ssUWEWdwJ";
        String url = "https://gitlab.com/api/v4/users";

        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", "Bearer " + token);
        headers.setContentType(MediaType.APPLICATION_JSON);

        HttpEntity<String> entity = new HttpEntity<>(headers);
        ResponseEntity<User[]> response = restTemplate.exchange(url, HttpMethod.GET, entity, User[].class);
        User[] users = response.getBody();

        return Arrays.asList(users);
    }

    public List<Comment> findCommentsByProjectIdAndIssueId(String id, String issueId){

        String token = "glpat-3Ty1dCruj56ssUWEWdwJ";
        String url = "https://gitlab.com/api/v4/projects/" + id + "/issues/" + issueId + "/notes";

        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", "Bearer " + token);
        headers.setContentType(MediaType.APPLICATION_JSON);

        HttpEntity<String> entity = new HttpEntity<>(headers);
        ResponseEntity<Comment[]> response = restTemplate.exchange(url, HttpMethod.GET, entity, Comment[].class);
        Comment[] comments = response.getBody();

        return Arrays.asList(comments);
    }

    public List<Commit> findCommitsByProjectId(String id){

        String token = "glpat-3Ty1dCruj56ssUWEWdwJ";
        String url = "https://gitlab.com/api/v4/projects/" + id + "/repository/commits";

        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", "Bearer " + token);
        headers.setContentType(MediaType.APPLICATION_JSON);

        HttpEntity<String> entity = new HttpEntity<>(headers);
        ResponseEntity<Commit[]> response = restTemplate.exchange(url, HttpMethod.GET, entity, Commit[].class);
        Commit[] commits = response.getBody();

        return Arrays.asList(commits);
    }

    public List<Issue> findIssuesByProjectId(String id){

        String token = "glpat-3Ty1dCruj56ssUWEWdwJ";
        String url = "https://gitlab.com/api/v4/projects/" + id + "/issues";

        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", "Bearer " + token);
        headers.setContentType(MediaType.APPLICATION_JSON);

        HttpEntity<String> entity = new HttpEntity<>(headers);
        ResponseEntity<Issue[]> response = restTemplate.exchange(url, HttpMethod.GET, entity, Issue[].class);
        Issue[] issues = response.getBody();

        return Arrays.asList(issues);
    }

    public List<Project> findAllProjects(){

        String token = "glpat-3Ty1dCruj56ssUWEWdwJ";
        String url = "https://gitlab.com/api/v4/projects";

        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", "Bearer " + token);
        headers.setContentType(MediaType.APPLICATION_JSON);

        HttpEntity<String> entity = new HttpEntity<>(headers);
        ResponseEntity<Project[]> response = restTemplate.exchange(url, HttpMethod.GET, entity, Project[].class);
        Project[] projects = response.getBody();

        return Arrays.asList(projects);
    }

    public Project findProjectById(String id){

        String token = "glpat-3Ty1dCruj56ssUWEWdwJ";
        String url = "https://gitlab.com/api/v4/projects/" + id;

        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", "Bearer " + token);
        headers.setContentType(MediaType.APPLICATION_JSON);

        HttpEntity<String> entity = new HttpEntity<>(headers);
        ResponseEntity<Project> response = restTemplate.exchange(url, HttpMethod.GET, entity, Project.class);
        Project project = response.getBody();

        return project;
    }

    public Project getProject(String id){

        String uri = "https://gitlab.com/api/v4/projects/" + id;

        Project dataProject = findProjectById(id);
        Project project = new Project(dataProject.getId().toString(), dataProject.getName(), dataProject.getWebUrl());

        List<Commit> dataCommit = findCommitsByProjectId(project.getId());
        List<Commit> commits = new ArrayList<>();
        for (Commit commit: dataCommit) {
            Commit newCommit = new Commit(
                    commit.getId(),
                    commit.getTitle(),
                    commit.getMessage(),
                    commit.getAuthorName(),
                    commit.getAuthorEmail(),
                    commit.getAuthoredDate(),
                    commit.getCommitterName(),
                    commit.getCommitterEmail(),
                    commit.getCommittedDate(),
                    commit.getWebUrl()
            );
            commits.add(newCommit);
        }
        project.setCommits(commits);

        List<Issue> dataIssue = findIssuesByProjectId(project.getId());
        List<Issue> issues = new ArrayList<>();
        for (Issue issue: dataIssue) {
            Issue newIssue = new Issue(
                    issue.getId(),
                    issue.getRefId(),
                    issue.getTitle(),
                    issue.getDescription(),
                    issue.getState(),
                    issue.getCreatedAt(),
                    issue.getUpdatedAt(),
                    issue.getClosedAt(),
                    issue.getLabels(),
                    issue.getAuthor(),
                    issue.getAssignee(),
                    issue.getUpvotes(),
                    issue.getDownvotes(),
                    issue.getWebUrl());

            List<Comment> dataComment = findCommentsByProjectIdAndIssueId(project.getId(), newIssue.getRefId());
            List<Comment> comments = new ArrayList<>();
            for (Comment comment: dataComment) {
                Comment newComment = new Comment(
                        comment.getId(),
                        comment.getBody(),
                        comment.getAuthor(),
                        comment.getCreatedAt(),
                        comment.getUpdatedAt()
                );
                comments.add(newComment);
            }
            newIssue.setComments(comments);
            issues.add(newIssue);
        }
        project.setIssues(issues);
        return project;
    }
}
