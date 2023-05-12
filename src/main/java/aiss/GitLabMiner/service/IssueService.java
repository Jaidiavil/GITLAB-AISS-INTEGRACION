package aiss.GitLabMiner.service;

import aiss.GitLabMiner.model.Comment;
import aiss.GitLabMiner.model.Commit;
import aiss.GitLabMiner.model.Issue;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.Arrays;
import java.util.List;

@Service
public class IssueService {

    @Autowired
    RestTemplate restTemplate;

    public List<Issue> getIssuesByProjectId(String id){

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

}
