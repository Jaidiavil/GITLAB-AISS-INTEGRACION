package aiss.GitLabMiner;

import aiss.GitLabMiner.exception.NotFoundExcept;
import aiss.GitLabMiner.model.Commit;
import aiss.GitLabMiner.model.Issue;
import aiss.GitLabMiner.model.Project;
import aiss.GitLabMiner.service.GitLabService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertFalse;

@SpringBootTest
class GitLabMinerApplicationTests {

	@Autowired
	GitLabService gitLabService;

	@Test
	@DisplayName("Get all projects")
	void findAllProjects() {
		List<Project> users = gitLabService.getProjects();
		assertFalse(users.isEmpty(), "The list of projects is empty!!");
		users.forEach(System.out::println);
	}
	@Test
	@DisplayName("Get Project")
	void findAProjectById() {
		String id = "21501483";
		Integer sinceCommits = 3;
		Integer sinceIssues = 20;
		Integer maxPages = 2;
		Project project = gitLabService.getProject(id,sinceCommits,sinceIssues,maxPages);
		System.out.println(project);

	}
	@Test
	@DisplayName("Get Commits")
	void findCommitsById() {
		String id = "21501483";
		Integer sinceCommits = 3;
		Integer maxPages = 2;
		List<Commit> commits = gitLabService.getCommits(id,sinceCommits,maxPages);
		System.out.println(commits);

	}
	@Test
	@DisplayName("Get Issues")
	void findIssuesById(){
		String id = "21501483";
		Integer sinceIssues = 20;
		Integer maxPages = 2;
		List<Issue> issues = gitLabService.getIssues(id,sinceIssues,maxPages);
		System.out.println(issues);

	}

}
