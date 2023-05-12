package aiss.GitLabMiner.Service;

import GitLabMiner.GitLabMiner.models.*;
import aiss.GitLabMiner.model.Commit;
import aiss.GitLabMiner.model.User;
import aiss.GitLabMiner.model.Comment;
import aiss.GitLabMiner.model.Issue;
import aiss.GitLabMiner.model.Project;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.Arrays;
import java.util.List;

@Service
public class GitLabService {

    @Autowired
    RestTemplate restTemplate;

    public List<Project> findAll(){
        List<Project> projects = null;
        String uri = "https://gitlab.com/api/v4/projects/";
        Project[] projectsSearch = restTemplate.getForObject(uri, Project[].class);

        return Arrays.asList(projectsSearch);
    }
    public Project findById(Integer id){
        Project project = null;
        String uri = "https://gitlab.com/api/v4/projects/" + id.toString();
        Project projectSearch = restTemplate.getForObject(uri, Project.class);
        project = projectSearch;
        project.setCommits(findCommitsById(id));
        System.out.println(project);
        project.setIssues(findIssuesById(id));

        return project;
    }
    public List<Commit> findCommitsById(Integer id){
        List<Commit> commits = null;
        String uri = "https://gitlab.com/api/v4/projects/" + id.toString() + "/repository/commits";
        Commit[] commitSearch = restTemplate.getForObject(uri, Commit[].class);

        return Arrays.asList(commitSearch);
    }
    public List<Issue> findIssuesById(Integer id){
        List<Issue> issues = null;
        String uri = "https://gitlab.com/api/v4/projects/" + id.toString() + "/issues";
        Issue[] issuesSearch = restTemplate.getForObject(uri, Issue[].class);
        for(Issue e: issuesSearch){
            e.setComments(findCommentByIid(id,e.getRefId()));
        }

        return Arrays.asList(issuesSearch);
    }
    public List<Comment> findCommentByIid(Integer id,Integer iid){
        List<Comment> issues = null;
        String uri = "https://gitlab.com/api/v4/projects/" + id.toString() + "/issues/" + iid.toString() + "/notes";
        Comment[] commentSearch = restTemplate.getForObject(uri, Comment[].class);
        Comment[] comments = commentSearch;
        return Arrays.asList(comments);
    }
    public Project createProject(Project project){
        String uri = "https://gitlab.com/api/v4/projects/";
        Project projectSearch = restTemplate.postForObject(uri, project, Project.class);

        return projectSearch;
    }
}