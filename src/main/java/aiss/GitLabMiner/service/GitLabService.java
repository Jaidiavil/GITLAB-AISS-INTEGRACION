package aiss.GitLabMiner.service;

import aiss.GitLabMiner.model.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;

@Service
public class GitLabService {

    @Autowired
    RestTemplate restTemplate;
    private final String token = "glpat-cbavaBZ6sJznUq8CP-oF";

    public List<Comment> getIssueComments(String id, String iid, Integer maxPages) {

        String uri = "https://gitlab.com/api/v4/projects/" + id + "/issues/" + iid + "/notes?per_page=" + maxPages;

        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", "Bearer " + token);
        headers.setContentType(MediaType.APPLICATION_JSON);

        HttpEntity<Comment> entity = new HttpEntity<>(null, headers);

        ResponseEntity<Comment[]> response;
        try {
            response = restTemplate.exchange(uri, HttpMethod.GET, entity, Comment[].class);
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Not found");
        }
        Comment[] comments = response.getBody();

        return Arrays.asList(comments);
    }

    public List<Commit> getCommits(String id, Integer sinceCommits, Integer maxPages) {

        String fecha = LocalDate.now().minusDays(sinceCommits).toString();
        String uri = "https://gitlab.com/api/v4/projects/" + id + "/repository/commits?since=" + fecha + "&per_page=" + maxPages;

        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", "Bearer " + token);
        headers.setContentType(MediaType.APPLICATION_JSON);

        HttpEntity<Commit> entity = new HttpEntity<>(null, headers);
        ResponseEntity<Commit[]> response;
        try {
            response = restTemplate.exchange(uri, HttpMethod.GET, entity, Commit[].class);
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Not found");
        }
        Commit[] commits = response.getBody();

        return Arrays.asList(commits);
    }

    public List<Issue> getIssues(String id, Integer sinceIssues, Integer maxPages) {

        String fecha = LocalDate.now().minusDays(sinceIssues).toString();
        String uri = "https://gitlab.com/api/v4/projects/" + id + "/issues?updated_before=" + fecha + "&per_page=" + maxPages;

        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", "Bearer " + token);
        headers.setContentType(MediaType.APPLICATION_JSON);

        HttpEntity<Issue> entity = new HttpEntity<>(null, headers);

        ResponseEntity<Issue[]> response;
        try {
            response = restTemplate.exchange(uri, HttpMethod.GET, entity, Issue[].class);
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Not found");
        }
        Issue[] issues = response.getBody();

        for (Issue i : issues) {

            i.setComments(getIssueComments(id, i.getIid(), maxPages));

        }

        return Arrays.asList(issues);
    }

    public Project getProject(String id, Integer sinceCommits,Integer sinceIssues,Integer maxPages) {

        String url = "https://gitlab.com/api/v4/projects/" + id;

        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", "Bearer " + token);
        headers.setContentType(MediaType.APPLICATION_JSON);

        HttpEntity<String> entity = new HttpEntity<>(url, headers);

        ResponseEntity<Project> response;
        try {
            response = restTemplate.exchange(url, HttpMethod.GET, entity, Project.class);
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Not found");
        }

        Project project = response.getBody();

        project.setCommits(getCommits(id, sinceCommits, maxPages));
        project.setIssues(getIssues(id, sinceIssues, maxPages));

        return project;
    }

    public List<Project> getProjects() {
        String uri = "https://gitlab.com/api/v4/projects/";

        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", "Bearer " + token);
        headers.setContentType(MediaType.APPLICATION_JSON);

        HttpEntity<Project> entity = new HttpEntity<>(null, headers);

        ResponseEntity<Project[]> response;
        try {
            response = restTemplate.exchange(uri, HttpMethod.GET, entity, Project[].class);
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Not found");
        }
        Project[] projects = response.getBody();

        return Arrays.asList(projects);
    }

    public Project postProject(Project project) {
        String url_post = "http://localhost:8080/gitminer";

        return restTemplate.postForObject(url_post, project, Project.class);


    }
}
