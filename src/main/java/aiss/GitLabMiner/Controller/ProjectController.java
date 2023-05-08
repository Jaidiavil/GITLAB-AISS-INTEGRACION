package aiss.GitLabMiner.Controller;
import aiss.GitLabMiner.model.Project;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import aiss.GitLabMiner.repository.ProjectRepository;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import javax.validation.Valid;




import java.util.List;

@RestController
@RequestMapping("/api/projects")
public class ProjectController {

    private ProjectRepository repository;
    public void ProjectRepository (ProjectRepository repository) {
        this.repository = repository;
    }

    public List<Project> findAll() {
        return repository.findAll();
    }

    @ResponseStatus(HttpStatus.NO_CONTENT)
    @PutMapping("/{id}")
    public void update(@PathVariable String id, @Valid @RequestBody Project updatedProject) {
        repository.update(updatedProject, id);
    }
}
