package vuttr.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import vuttr.domain.tool.Tool;

public interface ToolRepository extends JpaRepository<Tool, Long> {
    public boolean existsByTitle(String title);
}
