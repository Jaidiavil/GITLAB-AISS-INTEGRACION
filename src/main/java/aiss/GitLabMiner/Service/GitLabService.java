package aiss.GitLabMiner.Service;

import aiss.GitLabMiner.model.*;
import aiss.GitLabMiner.model.Commit;
import aiss.GitLabMiner.model.User;
import aiss.GitLabMiner.model.Comment;
import aiss.GitLabMiner.model.Issue;
import aiss.GitLabMiner.model.Project;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Service
public class GitLabService {

    public final List<Project> projects = new ArrayList<Project>();

    @Autowired
    RestTemplate restTemplate;

    public List<Project> findAll() {
        List<Project> projects = null;
        String uri = "https://gitlab.com/api/v4/projects/";
        Project[] projectsSearch = restTemplate.getForObject(uri, Project[].class);

        return Arrays.asList(projectsSearch);
    }

    public Project findId(String id) {
        Project project = null;
        String uri = "https://gitlab.com/api/v4/projects/" + id.toString();
        Project projectSearch = restTemplate.getForObject(uri, Project.class);
        project = projectSearch;
        project.setCommits(findCommitsById(id));
        System.out.println(project);
        project.setIssues(findIssuesById(id));

        return project;
    }

    public List<Commit> findCommitsById(String id) {
        List<Commit> commits = null;
        String uri = "https://gitlab.com/api/v4/projects/" + id.toString() + "/repository/commits";
        Commit[] commitSearch = restTemplate.getForObject(uri, Commit[].class);

        return Arrays.asList(commitSearch);
    }

    public List<Issue> findIssuesById(String id) {
        List<Issue> issues = null;
        String uri = "https://gitlab.com/api/v4/projects/" + id.toString() + "/issues";
        Issue[] issuesSearch = restTemplate.getForObject(uri, Issue[].class);
        for (Issue e : issuesSearch) {
            e.setComments(findCommentByIid(id, e.getRefId().toString()));
        }

        return Arrays.asList(issuesSearch);
    }

    public List<Comment> findCommentByIid(String id, String id2) {
        List<Comment> issues = null;
        String uri = "https://gitlab.com/api/v4/projects/" + id.toString() + "/issues/" + id2.toString() + "/notes";
        Comment[] commentSearch = restTemplate.getForObject(uri, Comment[].class);
        Comment[] comments = commentSearch;
        return Arrays.asList(comments);
    }

    public Project create(Project project) {
        String uri = "https://gitlab.com/api/v4/projects/";
        Project projectSearch = restTemplate.postForObject(uri, project, Project.class);

        return projectSearch;
    }

    public void update(Project updProject, String id) {
        Project existing = findId(id);
        int i = projects.indexOf(existing);
        updProject.setId(existing.getId());
        projects.set(i, updProject);
    }

    public void delete(String id) {
        projects.removeIf(project -> project.getId().equals(id));
    }
}



