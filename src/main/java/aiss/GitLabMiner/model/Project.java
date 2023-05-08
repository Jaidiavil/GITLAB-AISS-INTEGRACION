
package aiss.GitLabMiner.model;

import aiss.GitLabMiner.repository.ProjectRepository;
import com.fasterxml.jackson.annotation.JsonProperty;

import javax.naming.Name;
import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;


public class Project {
    public String id;
    public String name;
    public String webUrl;
    private List<Commit> commits;
    private List<Issue> issues;

    public Project() {
        this.commits = new ArrayList<>();
        this.issues = new ArrayList<>();;
    }
    public Project(String id, String name, String url) {
        this.id=id;
        this.name = name;
        this.webUrl = url;
        this.commits = new ArrayList<>();
        this.issues = new ArrayList<>();;
    }

    public Project(String name, String url) {
        this.name = name;
        this.webUrl = url;
        this.commits = new ArrayList<>();
        this.issues = new ArrayList<>();;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getWebUrl() {
        return webUrl;
    }

    public void setWebUrl(String webUrl) {
        this.webUrl = webUrl;
    }

    public List<Commit> getCommits() {
        return commits;
    }

    public void setCommits(List<Commit> commits) {
        this.commits = commits;
    }

    public List<Issue> getIssues() {
        return issues;
    }

    public void setIssues(List<Issue> issues) {
        this.issues = issues;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(Project.class.getName()).append('@').append(Integer.toHexString(System.identityHashCode(this))).append('[');
        sb.append("id");
        sb.append('=');
        sb.append(((this.id == null) ? "<null>" : this.id));
        sb.append(',');
        sb.append("commits");
        sb.append('=');
        sb.append(((this.commits == null) ? "<null>" : this.commits));
        sb.append(',');
        sb.append("issues");
        sb.append('=');
        sb.append(((this.issues == null) ? "<null>" : this.issues));
        sb.append(',');

        if (sb.charAt((sb.length() - 1)) == ',') {
            sb.setCharAt((sb.length() - 1), ']');
        } else {
            sb.append(']');
        }
        return sb.toString();
    }

}
