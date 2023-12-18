package vuttr.domain.tool;

import io.swagger.v3.oas.annotations.media.Schema;
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
    @Schema(example = "1")
    private Long id;

    @Schema(example = "notion")
    private String title;

    @Schema(example = "https://www.notion.so/")
    private String link;

    @Schema(example = "Very useful notes, cheat sheets, plans and whatever you need to write down")
    private String description;

    @Schema(example = "[\"Notes\", \"Studying\", \"Planning\", \"Organization\"]")
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
