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
    private final String url_post = "http://localhost:8080/gitminer/projects";
    private final String token = "glpat-cbavaBZ6sJznUq8CP-oF";

    public List<Comment> getIssueComments(String id, String ref_id, Integer maxPages){

        String uri = "https://gitlab.com/api/v4/projects/" + id + "/issues/" + ref_id + "/notes?per_page=" + maxPages;

        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", "Bearer " + token);
        headers.setContentType(MediaType.APPLICATION_JSON);

        HttpEntity<Comment> entity = new HttpEntity<Comment>(null, headers);
        ResponseEntity<Comment[]> response = restTemplate.exchange(uri, HttpMethod.GET, entity, Comment[].class);
        Comment[] comments = response.getBody();

        return Arrays.asList(comments);
    }

    public List<Commit> getCommits(String id, Integer sinceCommits, Integer maxPages) {

        String fecha = LocalDate.now().minusDays(sinceCommits).toString();
        String uri = "https://gitlab.com/api/v4/projects/" + id + "/repository/commits?since=" + fecha +"&per_page=" + maxPages;

        Commit[] response = restTemplate.getForObject(uri, Commit[].class);
        List<Commit> commits= Arrays.asList(response);

        return commits;
    }

    public List<Issue> getIssues(String id, Integer sinceIssues, Integer maxPages) {

        String fecha = LocalDate.now().minusDays(sinceIssues).toString();
        String uri = "https://gitlab.com/api/v4/projects/" + id + "/issues?updated_before="+fecha+"&per_page=" + maxPages;

        Issue[] response = restTemplate.getForObject(uri, Issue[].class);

        for(Issue i: response){

            i.setComments(getIssueComments(id,i.getRefId(),maxPages));

        }

        return Arrays.asList(response);
    }

    public Project getProject(String id, Integer sinceCommits,Integer sinceIssues,Integer maxPages){

        String url = "https://gitlab.com/api/v4/projects/" + id;

        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", "Bearer " + token);
        headers.setContentType(MediaType.APPLICATION_JSON);

        HttpEntity<String> entity = new HttpEntity<>(url,headers);
        ResponseEntity<Project> response = restTemplate.exchange(url, HttpMethod.GET, entity, Project.class);

        Project project = response.getBody();
        project.setCommits(getCommits(id,sinceCommits,maxPages));
        project.setIssues(getIssues(id,sinceIssues,maxPages));

        return project;
    }

    public List<Project> getProjects(){
        String uri = "https://gitlab.com/api/v4/projects/";
        Project[] response = restTemplate.getForObject(uri, Project[].class);

        return Arrays.asList(response);
    }

    public Project postProject(Project project) {
        String url = url_post;
        Project response = restTemplate.postForObject(url, project, Project.class);

        return response;
    }
}
