package aiss.GitLabMiner.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import aiss.GitLabMiner.repository.ProjectRepository;

@RestController
@RequestMapping("/api/projects")
public class ProjectController {

    private ProjectRepository repository;
    public void ProjectRepository (ProjectRepository repository) {
        this.repository = repository;
    }

}
