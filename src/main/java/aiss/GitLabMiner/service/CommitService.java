package aiss.GitLabMiner.service;

import aiss.GitLabMiner.model.Commit;
import aiss.GitLabMiner.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.Arrays;
import java.util.List;

@Service
public class CommitService {

    @Autowired
    RestTemplate restTemplate;

    public List<Commit> getCommitsByProjectId(String id){

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

}
