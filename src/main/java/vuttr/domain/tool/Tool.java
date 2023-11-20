package vuttr.domain.tool;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Table(name = "tools")
@Entity
@NoArgsConstructor
@Getter
@Setter
public class Tool {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String title;
    private String link;
    private String description;
    private List<String> tags;

    public Tool(String title, String link, String description, List<String> tags) {
        this.title = title;
        this.link = link;
        this.description = description;
        this.tags = tags;
    }
    public Tool(ToolDTO toolDTO) {
        this.title = toolDTO.title();
        this.link = toolDTO.link();
        this.description = toolDTO.description();
        this.tags = toolDTO.tags();
    }
}
