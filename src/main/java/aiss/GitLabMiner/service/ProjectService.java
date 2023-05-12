package aiss.GitLabMiner.service;

import aiss.GitLabMiner.model.Project;
import aiss.GitLabMiner.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.Arrays;
import java.util.List;

@Service
public class ProjectService {

    @Autowired
    RestTemplate restTemplate;

    public List<Project> getProjects(){

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

    public Project getProjectById(String id){

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

    public Project postProject(){

        String token = "glpat-3Ty1dCruj56ssUWEWdwJ";
        String url = "https://gitlab.com/api/v4/projects";

        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", "Bearer " + token);
        headers.setContentType(MediaType.APPLICATION_JSON);

        HttpEntity<String> entity = new HttpEntity<>(headers);
        ResponseEntity<Project> response = restTemplate.exchange(url, HttpMethod.POST, entity, Project.class);
        Project project = response.getBody();

        return project;
    }


}
