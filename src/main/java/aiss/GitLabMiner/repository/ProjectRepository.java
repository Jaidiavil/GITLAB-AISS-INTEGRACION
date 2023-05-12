package aiss.GitLabMiner.repository;

import aiss.GitLabMiner.model.Project;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProjectRepository extends JpaRepository<Project, Long> {
}
