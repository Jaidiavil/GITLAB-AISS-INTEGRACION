package aiss.GitLabMiner.service;

import aiss.GitLabMiner.model.Comment;
import aiss.GitLabMiner.model.Commit;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.Arrays;
import java.util.List;


@Service
public class CommentService {
    @Autowired
    RestTemplate restTemplate;

    public List<Comment> getCommentsByProjectIdAndIssueId(String id, String issueId){

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

}
