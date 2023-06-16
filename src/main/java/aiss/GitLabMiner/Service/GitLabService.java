package aiss.GitLabMiner.service;

import aiss.GitLabMiner.model.*;
import aiss.GitLabMiner.util.Utils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Service
public class GitLabService {

    @Autowired
    RestTemplate restTemplate;
    public GitLabService(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }
    private final String url_post = "http://localhost:8080/gitminer/projects";
    private final String token = "glpat-cbavaBZ6sJznUq8CP-oF";

    public List<Comment> getIssueComments(String id, String issueId, Integer maxPages){

        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", "Bearer " + token);
        headers.setContentType(MediaType.APPLICATION_JSON);

        List<Comment> comments = new ArrayList<>();

        for (int i = 1; i<=maxPages; i++) {

            String url = "https://gitlab.com/api/v4/projects/" + id + "/issues/" + issueId + "/notes?page=" + i;

            HttpEntity<String> entity = new HttpEntity<>(url,headers);
            ResponseEntity<Comment[]> response = restTemplate.exchange(url, HttpMethod.GET, entity, Comment[].class);

            comments.addAll(new ArrayList<>(Arrays.asList(response.getBody())));
        }

        return comments;
    }

    public List<Commit> getCommits(String id, Integer sinceCommits, Integer maxPages) {

        String fecha = LocalDate.now().minusDays(sinceCommits).toString();

        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", "Bearer " + token);
        headers.setContentType(MediaType.APPLICATION_JSON);

        List<Commit> commits = new ArrayList<>();

        for (int i = 1; i<=maxPages; i++) {

            String url = "https://gitlab.com/api/v4/projects/" + id + "/repository/commits?since=" + fecha + "&page=" + i;

            HttpEntity<String> entity = new HttpEntity<>(url,headers);
            ResponseEntity<Commit[]> response = restTemplate.exchange(url, HttpMethod.GET, entity, Commit[].class);

            commits.addAll(new ArrayList<>(Arrays.asList(response.getBody())));
        }

        return commits;
    }

    public List<Issue> getIssues(String id, Integer sinceIssues, Integer maxPages) {

        String fecha = LocalDate.now().minusDays(sinceIssues).toString();

        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", "Bearer " + token);
        headers.setContentType(MediaType.APPLICATION_JSON);

        List<Issue> issues = new ArrayList<>();

        for (int i = 1; i<=maxPages; i++) {

            String url = "https://gitlab.com/api/v4/projects/" + id + "/issues?updated_after=" + fecha + "&page=" + i;

            HttpEntity<String> entity = new HttpEntity<>(url,headers);
            ResponseEntity<Issue[]> response = restTemplate.exchange(url, HttpMethod.GET, entity, Issue[].class);

            issues.addAll(new ArrayList<>(Arrays.asList(response.getBody())));

        }
        return issues;
    }

    public Project getProject (String id){

        String url = "https://gitlab.com/api/v4/projects/" + id;

        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", "Bearer " + token);
        headers.setContentType(MediaType.APPLICATION_JSON);

        HttpEntity<String> entity = new HttpEntity<>(url,headers);
        ResponseEntity<Project> response = restTemplate.exchange(url, HttpMethod.GET, entity, Project.class);
        Project project = response.getBody();

        return project;
    }

    public void postProject(Project project) {
        RestTemplate restTemplate = new RestTemplate();
        String url = url_post;
        restTemplate.postForObject(url, project, Project.class);
    }
}
